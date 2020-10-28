package ru.vtb.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestDto {

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;

    @NotNull
    private Double distance;
}
