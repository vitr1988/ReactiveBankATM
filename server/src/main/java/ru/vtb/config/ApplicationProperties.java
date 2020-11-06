package ru.vtb.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.core.io.Resource;

@Value
@ConstructorBinding
@ConfigurationProperties("application")
public class ApplicationProperties {

    Resource atmInfoUrl;
    Integer chunkSize;
}
