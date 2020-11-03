package ru.vtb.config.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.util.StringUtils;
import ru.vtb.dto.CompanyDto;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CompanyConverter implements Converter {

    @Override
    public boolean canConvert(Class type) {
        return CompanyDto.class.isAssignableFrom(type);
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        // Don't do anything
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        val companyDto = new CompanyDto();
        
        reader.moveDown();
        companyDto.setCompanyId(reader.getValue());

        reader.moveUp();
        reader.moveDown();
        companyDto.setName(reader.getValue());

        reader.moveUp();
        reader.moveDown();
        companyDto.setCountry(reader.getValue());

        reader.moveUp();
        reader.moveDown();
        companyDto.setAddress(reader.getValue());

        reader.moveUp();
        reader.moveDown();
        companyDto.setAddressAdd(reader.getValue());

        reader.moveUp();
        reader.moveDown();
        companyDto.setWorkingTime(reader.getValue());

        reader.moveUp();
        reader.moveDown();
        if (reader.hasMoreChildren()) {
            double[] location = new double[2]; // [long, lat]
            reader.moveDown();
            location[1] = toDouble(reader.getValue()); // latitude

            reader.moveUp();
            reader.moveDown();
            location[0] = toDouble(reader.getValue()); // longitude
            reader.moveUp();
            companyDto.setLocation(location);
        }

        reader.moveUp();
        reader.moveDown();
        companyDto.setUrl(toURL(reader.getValue()));

        reader.moveUp();
        reader.moveDown();
        companyDto.setRubricId(toLong(reader.getValue()));

        reader.moveUp();
        reader.moveDown();
        companyDto.setActualizationDate(toTime(toLong(reader.getValue())));

        return companyDto;
    }

    private LocalDateTime toTime(Long epochInMilli) {
        return Instant.ofEpochSecond(epochInMilli).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @SneakyThrows
    private URL toURL(String url) {
        return StringUtils.isEmpty(url) ? null : new URL(url);
    }

    private Long toLong(String value) {
        return Long.parseLong(value);
    }

    private Double toDouble(String value) {
        return Double.valueOf(value);
    }
}