package ru.vtb.repository;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.vtb.model.Company;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("Репозиторий для работы с банкоматами ")
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository repository;

    @Test
    @SneakyThrows
    @DisplayName("уметь сохранять информации о них")
    public void shouldSaveNewGenre() {
        val company = getCompany(null);

        Mono<Company> companyMono = repository.save(company);

        StepVerifier
                .create(companyMono)
                .assertNext(genre -> assertThat(genre.getId()).isNotEmpty())
                .expectComplete()
                .verify();
    }

    @Test
    @SneakyThrows
    @DisplayName("уметь сохранять информацию и получать по идентификатору")
    public void shouldSaveAndFindByCode() {
        val id = "UID";
        val company = getCompany(id);
        repository.save(company).subscribe(System.out::println);

        StepVerifier.create(repository.findById(id))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    private Company getCompany(String id) throws MalformedURLException {
        val company = new Company();
        company.setId(id);
        company.setCompanyId("Test Company Id");
        company.setRubricId(new Random().nextLong());
        company.setWorkingTime("Test working time");
        company.setAddress("Test address");
        company.setAddressAdd("Test additional address");
        company.setActualizationDate(LocalDateTime.now());
        company.setLocation(new double[]{50.0, 50.0});
        company.setCountry("Russia");
        company.setUrl(new URL("https://www.example.org"));
        return company;
    }
}
