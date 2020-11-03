package ru.vtb.feign;

import org.springframework.stereotype.Component;
import ru.vtb.dto.CompanyDto;

import java.util.Collections;
import java.util.List;

@Component
public class CompanyFallback implements CompanyClient {

    @Override
    public List<CompanyDto> getAtms(Float longitude, Float latitude, Float distance) {
        return Collections.singletonList(CompanyDto.NO_COMPANY);
    }
}
