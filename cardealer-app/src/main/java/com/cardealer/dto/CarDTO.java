package com.cardealer.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CarDTO {
    @NotBlank
    private String make;
    @NotBlank
    private String model;
    @Min(1700)
    private Integer year;
    @Min(0)
    private Double price;

    public CarDTO(String make, String model, Integer year, Double price) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
