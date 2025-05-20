package ru.rogzy.tracker_backend.controller.models;


import lombok.Data;

@Data
public class FoodLogDTO {
    private Long userId;
    private String name;
    private Float prot;
    private Float carb;
    private Float fat;
    private Float calorage;
    private Float weight;
    private Long foodId;
    private Long favoriteFoodId;
}