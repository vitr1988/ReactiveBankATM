package ru.vtb.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import ru.vtb.config.converter.CompanyConverter;
import ru.vtb.dto.CompanyDto;
import ru.vtb.mapper.CompanyMapper;
import ru.vtb.model.Company;
import ru.vtb.repository.CompanyRepository;

import java.util.Map;

@Configuration
public class JobConfiguration {

    public static final String DB_INITIALIZATION_JOB = "dbInitializationJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    private final Resource atmInfoResource;
    private final Integer chunkSize;

    public JobConfiguration(ApplicationProperties applicationProperties, JobBuilderFactory jobBuilderFactory,
                            StepBuilderFactory stepBuilderFactory, CompanyRepository companyRepository,
                            CompanyMapper companyMapper) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;

        this.atmInfoResource = applicationProperties.getAtmInfoUrl();
        this.chunkSize = applicationProperties.getChunkSize();
    }

    @Bean
    public StaxEventItemReader<CompanyDto> companyItemReader(){
        Map<String, Class> aliases = Map.of("company", CompanyDto.class);

        XStreamMarshaller ummarshaller = new XStreamMarshaller();
        ummarshaller.setAliases(aliases);
        ummarshaller.setConverters(new CompanyConverter());

        StaxEventItemReader<CompanyDto> reader = new StaxEventItemReader<>();
        reader.setResource(atmInfoResource);
        reader.setFragmentRootElementName("company");
        reader.setUnmarshaller(ummarshaller);

        return reader;
    }

    @Bean
    public ItemWriter<Company> companyWriter() {
        return companies -> companyRepository.saveAll(companies).subscribe(System.out::println);
    }

    @Bean
    public ItemProcessor<CompanyDto, Company> companyProcessor() {
        return companyMapper::toDocument;
    }

    @Bean
    public Step loadDb() {
        return stepBuilderFactory.get("loadDb")
                .<CompanyDto, Company>chunk(chunkSize)
                .reader(companyItemReader())
                .processor(companyProcessor())
                .writer(companyWriter())
                .build();
    }

    @Bean
    public Job job(Step clearDbStep, Step loadDb) {
        return jobBuilderFactory
                .get(DB_INITIALIZATION_JOB)
                .incrementer(new RunIdIncrementer())
                .start(clearDbStep)
                .next(loadDb)
                .build();
    }

    @Bean
    public Step clearDbStep(Tasklet tasklet) {
        return this.stepBuilderFactory.get("clearDbStep")
                .tasklet(tasklet)
                .build();
    }

    @Bean
    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            companyRepository.deleteAll().subscribe();
            return RepeatStatus.FINISHED;
        };
    }
}
