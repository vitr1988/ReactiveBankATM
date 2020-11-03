package ru.vtb.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.telegram.telegrambots.meta.api.objects.Location;
import ru.vtb.dto.CompanyDto;

import java.util.List;

//TODO: добавить RibbonClient
@FeignClient(value = "bank-atm", url = "http://env-4382930.mircloud.host")
public interface CompanyClient {

    @GetMapping("/api/atms")
    List<CompanyDto> getAtms(@RequestParam Float longitude, @RequestParam Float latitude, @RequestParam Float distance);

    default List<CompanyDto> getAtms(Location location, Float distance) {
        return getAtms(location.getLongitude(), location.getLatitude(), distance);
    }
}
