package ru.rogzy.tracker_backend.controller.models;

import lombok.Data;

@Data
public class FavoriteFoodDTO {
    private Long userId;
    private Long foodId;
    private String name;
    private Float overrideProt;
    private Float overrideCarb;
    private Float overrideFat;
    private Float overrideCalorage;
}