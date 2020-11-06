package ru.vtb.config;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vtb.feign.CompanyClient;

@Configuration
public class LoadBalancerConfiguration {

    @Bean
    IPing apiPing(@Value(CompanyClient.CONTEXT_PATH) String contextPath) {
        return new PingUrl(false, contextPath + "/actuator/health");
    }

    @Bean
    IRule apiRule() {
        return new RoundRobinRule();
    }
}
