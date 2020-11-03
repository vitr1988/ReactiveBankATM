package ru.vtb.mapper;

import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.vtb.dto.CompanyDto;
import ru.vtb.model.Company;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-11-03T09:18:07+0400",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public CompanyDto toDto(Company entity) {
        if ( entity == null ) {
            return null;
        }

        CompanyDto companyDto = new CompanyDto();

        companyDto.setCompanyId( entity.getCompanyId() );
        companyDto.setName( entity.getName() );
        companyDto.setCountry( entity.getCountry() );
        companyDto.setAddress( entity.getAddress() );
        companyDto.setAddressAdd( entity.getAddressAdd() );
        companyDto.setWorkingTime( entity.getWorkingTime() );
        double[] location = entity.getLocation();
        if ( location != null ) {
            companyDto.setLocation( Arrays.copyOf( location, location.length ) );
        }
        companyDto.setUrl( entity.getUrl() );
        companyDto.setRubricId( entity.getRubricId() );
        companyDto.setActualizationDate( entity.getActualizationDate() );

        return companyDto;
    }

    @Override
    public Company toEntity(CompanyDto entity) {
        if ( entity == null ) {
            return null;
        }

        Company company = new Company();

        company.setCompanyId( entity.getCompanyId() );
        company.setName( entity.getName() );
        company.setCountry( entity.getCountry() );
        company.setAddress( entity.getAddress() );
        company.setAddressAdd( entity.getAddressAdd() );
        company.setWorkingTime( entity.getWorkingTime() );
        double[] location = entity.getLocation();
        if ( location != null ) {
            company.setLocation( Arrays.copyOf( location, location.length ) );
        }
        company.setUrl( entity.getUrl() );
        company.setRubricId( entity.getRubricId() );
        company.setActualizationDate( entity.getActualizationDate() );

        return company;
    }
}
