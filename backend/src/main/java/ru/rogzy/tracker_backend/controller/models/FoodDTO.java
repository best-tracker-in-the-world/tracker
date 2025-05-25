package ru.rogzy.tracker_backend.controller.models;

import lombok.Data;

@Data
public class FoodDTO {
    private String name;
    private Float prot;
    private Float carb;
    private Float fat;
    private Float calorage;
    private Long defaultWeight;
}

