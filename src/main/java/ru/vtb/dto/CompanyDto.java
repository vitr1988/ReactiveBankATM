package ru.vtb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.net.URL;
import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({ "id", "name", "country", "address", "addressDetails", "workingTime", "location", "externalId", "actualizationDate" })
public class CompanyDto {

    @JsonProperty("id")
    private String companyId;

    private String name;
    private String country;
    private String address;

    @JsonProperty("addressDetails")
    private String addressAdd;
    private String workingTime;
    private double[] location;

    @JsonIgnore
    private URL url;

    @JsonProperty("externalId")
    private Long rubricId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime actualizationDate;
}
