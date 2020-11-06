package ru.vtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.vtb.config.ApplicationProperties;

@EnableCircuitBreaker
@EnableFeignClients
@EnableConfigurationProperties(ApplicationProperties.class)
@SpringBootApplication
public class TelegramAtm {

    public static void main(String[] args) {
        SpringApplication.run(TelegramAtm.class, args);
    }
}
