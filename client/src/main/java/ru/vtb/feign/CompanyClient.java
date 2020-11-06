package ru.vtb.feign;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.telegram.telegrambots.meta.api.objects.Location;
import ru.vtb.config.LoadBalancerConfiguration;
import ru.vtb.dto.CompanyDto;

import java.util.List;

import static ru.vtb.rest.ApiConstant.*;

@FeignClient(name = CompanyClient.CLIENT_NAME, path = CompanyClient.CONTEXT_PATH, fallback = CompanyFallback.class)
@RibbonClient(name = CompanyClient.CLIENT_NAME, configuration = LoadBalancerConfiguration.class)
public interface CompanyClient {

    String CLIENT_NAME = "bank-atm";
    String CONTEXT_PATH = "${bank-atm.api.context-path:/}";

    @GetMapping(ATM_SEARCH_URL)
    List<CompanyDto> getAtms(@RequestParam(LONGITUDE_REQUEST_PARAM) Float longitude,
                             @RequestParam(LATITUDE_REQUEST_PARAM) Float latitude,
                             @RequestParam(DISTANCE_REQUEST_PARAM) Float distance);

    default List<CompanyDto> getAtms(Location location, Float distance) {
        return getAtms(location.getLongitude(), location.getLatitude(), distance);
    }
}
