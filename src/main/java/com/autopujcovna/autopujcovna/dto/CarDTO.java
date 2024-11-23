package com.autopujcovna.autopujcovna.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter

public class CarDTO {

    @NotBlank(message = "Značka vozidla je povinná.")
    private String brand;

    @NotBlank(message = "Model vozidla je povinný.")
    private String model;

    @NotNull(message = "Rok výroby je povinný.")
    @Min(value = 1950, message = "Rok výroby musí být větší než 1950.")
    private Integer year;

    @NotNull(message = "Stav kilometrů je povinný.")
    @Positive(message = "Stav kilometrů musí být kladné číslo.")
    private Integer kmStatus;

    @NotBlank(message = "Barva vozidla je povinná.")
    private String color;

}

