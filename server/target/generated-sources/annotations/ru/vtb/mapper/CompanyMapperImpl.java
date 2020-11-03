package ru.vtb.mapper;

import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.vtb.dto.CompanyDto;
import ru.vtb.model.Company;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-11-03T13:26:33+0400",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public CompanyDto toDto(Company document) {
        if ( document == null ) {
            return null;
        }

        CompanyDto companyDto = new CompanyDto();

        companyDto.setCompanyId( document.getCompanyId() );
        companyDto.setName( document.getName() );
        companyDto.setCountry( document.getCountry() );
        companyDto.setAddress( document.getAddress() );
        companyDto.setAddressAdd( document.getAddressAdd() );
        companyDto.setWorkingTime( document.getWorkingTime() );
        double[] location = document.getLocation();
        if ( location != null ) {
            companyDto.setLocation( Arrays.copyOf( location, location.length ) );
        }
        companyDto.setUrl( document.getUrl() );
        companyDto.setRubricId( document.getRubricId() );
        companyDto.setActualizationDate( document.getActualizationDate() );

        return companyDto;
    }

    @Override
    public Company toDocument(CompanyDto dto) {
        if ( dto == null ) {
            return null;
        }

        Company company = new Company();

        company.setCompanyId( dto.getCompanyId() );
        company.setName( dto.getName() );
        company.setCountry( dto.getCountry() );
        company.setAddress( dto.getAddress() );
        company.setAddressAdd( dto.getAddressAdd() );
        company.setWorkingTime( dto.getWorkingTime() );
        double[] location = dto.getLocation();
        if ( location != null ) {
            company.setLocation( Arrays.copyOf( location, location.length ) );
        }
        company.setUrl( dto.getUrl() );
        company.setRubricId( dto.getRubricId() );
        company.setActualizationDate( dto.getActualizationDate() );

        return company;
    }
}
