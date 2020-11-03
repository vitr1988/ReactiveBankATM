package ru.vtb.service;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import reactor.core.publisher.Flux;
import ru.vtb.dto.CompanyDto;

public interface CompanyService {

    Flux<CompanyDto> getCompaniesByCoordinatesAndDistance(@Parameter(in = ParameterIn.QUERY, name = "longitude", description = "долгота", example = "37") double longitude,
                                                          @Parameter(in = ParameterIn.QUERY, name = "latitude", description = "широта", example = "55") double latitude,
                                                          @Parameter(in = ParameterIn.QUERY, name = "distance", description = "радиус дистанции", example = "20") double distance);
}
