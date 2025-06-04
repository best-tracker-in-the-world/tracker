package ru.rogzy.tracker_backend.controller.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodLogDTO {
    private Long id;
    private String name;
    private Float prot;
    private Float carb;
    private Float fat;
    private Float calorage;
    private Float weight = 100.0F;
    private Boolean addToFavorites;
}