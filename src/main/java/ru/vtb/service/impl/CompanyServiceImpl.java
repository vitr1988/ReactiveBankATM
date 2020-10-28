package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.vtb.dto.CompanyDto;
import ru.vtb.mapper.CompanyMapper;
import ru.vtb.repository.CompanyRepository;
import ru.vtb.service.CompanyService;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public Flux<CompanyDto> getCompaniesByCoordinatesAndDistance(double longitude, double latitude, double distance) {
        return companyRepository.findByLocationNear(
                new Point(longitude, latitude),
                new Distance(distance, Metrics.KILOMETERS))
                .map(GeoResult::getContent)
                .map(companyMapper::toDto);
    }
}
