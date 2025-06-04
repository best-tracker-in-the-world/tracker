package ru.rogzy.tracker_backend.controller.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightLogForDayResponseDTO {
    private Long currentId;
    private Float current;
    private Long previousId;
    private Float previous;
}
