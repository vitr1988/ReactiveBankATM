package ru.vtb.config;

import lombok.Getter;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Value
@Getter
@ConstructorBinding
@ConfigurationProperties("bot")
public class ApplicationProperties {

    private String name;
    private String token;
}
