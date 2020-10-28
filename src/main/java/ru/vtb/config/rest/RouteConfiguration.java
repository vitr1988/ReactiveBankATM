package ru.vtb.config.rest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;
import ru.vtb.dto.CompanyDto;
import ru.vtb.service.CompanyService;

import java.net.URI;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static ru.vtb.config.JobConfiguration.DB_INITIALIZATION_JOB;
import static ru.vtb.config.rest.ApiConstant.*;

@Configuration
@RequiredArgsConstructor
public class RouteConfiguration {

    private final CompanyService companyService;

    private final Job dbMigrationJob;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    @Bean
    @RouterOperations({
        @RouterOperation(path = ATM_SEARCH_URL, beanClass = CompanyService.class, beanMethod = "getCompaniesByCoordinatesAndDistance"),
    })
    public RouterFunction<ServerResponse> composedRoutes() {
        return route()
                .GET("/", req -> ServerResponse.temporaryRedirect(URI.create("/swagger-ui.html")).build())
                .GET(ATM_SEARCH_URL, hasQueryParam(LONGITUDE_REQUEST_PARAM).and(hasQueryParam(LATITUDE_REQUEST_PARAM)).and(hasQueryParam(DISTANCE_REQUEST_PARAM)),
                        request -> {
                            Optional<String> longitude = request.queryParam(LONGITUDE_REQUEST_PARAM);
                            Optional<String> latitude = request.queryParam(LATITUDE_REQUEST_PARAM);
                            Optional<String> distance = request.queryParam(DISTANCE_REQUEST_PARAM);
                            if (longitude.isEmpty() || latitude.isEmpty() || distance.isEmpty()) {
                                return badRequest().build();
                            } else {
                                return ok().contentType(APPLICATION_JSON).body(
                                        companyService.getCompaniesByCoordinatesAndDistance(
                                                toDouble(longitude),
                                                toDouble(latitude),
                                                toDouble(distance)), CompanyDto.class);
                            }
                        }
                )
                .POST(REFRESH_URL, this::refreshData)
                .GET(STATUS_URL, accept(APPLICATION_JSON), this::status)
                .build();
    }

    @SneakyThrows
    private Mono<ServerResponse> refreshData(ServerRequest request) {
        JobExecution execution = jobLauncher.run(dbMigrationJob, new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
        return ok().body(fromValue(execution.getJobId()));
    }
    private Mono<ServerResponse> status(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON).body(fromValue(jobExplorer.getLastJobInstance(DB_INITIALIZATION_JOB)));
    }

    private static RequestPredicate hasQueryParam(String name) {
        return RequestPredicates.queryParam(name, StringUtils::hasText);
    }

    private static Double toDouble(Optional<String> stringOptional) {
        return stringOptional.map(Double::valueOf).orElse(null);
    }
}
