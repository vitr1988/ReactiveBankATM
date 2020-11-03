package ru.vtb.config.rest;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;

import static ru.vtb.rest.ApiConstant.*;

@SpringBootTest
@DisplayName("Контроллер банкоматов в реактивном стиле должен ")
public class CompanyRoutingTest {

    @Autowired
    @Qualifier("composedRoutes")
    private RouterFunction<ServerResponse> routerFunction;

    @Test
    @DisplayName("уметь проверять доступность эндпойнта")
    public void testApiRoute() {
        val client = WebTestClient
                .bindToRouterFunction(routerFunction)
                .build();

        val uriComponents = UriComponentsBuilder.newInstance()
                .path(ATM_SEARCH_URL)
                .queryParam(LONGITUDE_REQUEST_PARAM, 50)
                .queryParam(LATITUDE_REQUEST_PARAM, 50)
                .queryParam(DISTANCE_REQUEST_PARAM, 1).build();

        client.get()
                .uri(uriComponents.toUriString())
                .exchange()
                .expectStatus()
                .isOk();
    }
}
