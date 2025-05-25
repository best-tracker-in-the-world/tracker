package ru.rogzy.tracker_backend.repository.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table("foods")
public class FoodDO {
    @Id
    private Long id;
    @Column("created_at")
    private Instant createdAt;
    @Column("updated_at")
    private Instant updatedAt;
    private String name;
    private Float prot;
    private Float carb;
    private Float fat;
    private Float calorage;
    @Column("default_weight")
    private Long defaultWeight;
}

