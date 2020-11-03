package ru.vtb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.vtb.rest.ApiConstant.ATM_SEARCH_URL;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ATM API")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("https://www.vtb.ru")));
    }

    @Bean
    public GroupedOpenApi atmOpenApi() {
        String[] paths = { ATM_SEARCH_URL };
        return GroupedOpenApi.builder().group("atms").pathsToMatch(paths).build();
    }
}
