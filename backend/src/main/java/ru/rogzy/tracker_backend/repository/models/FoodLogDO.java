package ru.rogzy.tracker_backend.repository.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table("food_log")
public class FoodLogDO {
    @Id
    private Long id;
    @Column("created_at")
    private Instant createdAt;
    @Column("updated_at")
    private Instant updatedAt;
    @Column("user_id")
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
