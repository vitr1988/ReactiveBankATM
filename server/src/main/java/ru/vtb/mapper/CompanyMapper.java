package ru.vtb.mapper;

import org.mapstruct.Mapper;
import ru.vtb.dto.CompanyDto;
import ru.vtb.model.Company;

@Mapper
public interface CompanyMapper extends AbstractMapper<Company, CompanyDto> {
}
