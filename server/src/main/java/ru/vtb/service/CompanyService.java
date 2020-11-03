package ru.vtb.service;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import reactor.core.publisher.Flux;
import ru.vtb.dto.CompanyDto;

import static ru.vtb.rest.ApiConstant.*;

public interface CompanyService {

    Flux<CompanyDto> getCompaniesByCoordinatesAndDistance(@Parameter(in = ParameterIn.QUERY, name = LONGITUDE_REQUEST_PARAM, description = "долгота", example = "37") double longitude,
                                                          @Parameter(in = ParameterIn.QUERY, name = LATITUDE_REQUEST_PARAM, description = "широта", example = "55") double latitude,
                                                          @Parameter(in = ParameterIn.QUERY, name = DISTANCE_REQUEST_PARAM, description = "радиус дистанции", example = "20") double distance);
}
