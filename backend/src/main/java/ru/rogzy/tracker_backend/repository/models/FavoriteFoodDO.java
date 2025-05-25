package ru.rogzy.tracker_backend.repository.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table("favorite_foods")
public class FavoriteFoodDO {
    @Id
    private Long id;
    @Column("created_at")
    private Instant createdAt;
    @Column("updated_at")
    private Instant updatedAt;
    @Column("user_id")
    private Long userId;
    @Column("food_id")
    private Long foodId;
    private String name;
    @Column("override_prot")
    private Float overrideProt;
    @Column("override_carb")
    private Float overrideCarb;
    @Column("override_fat")
    private Float overrideFat;
    @Column("override_calorage")
    private Float overrideCalorage;
}

