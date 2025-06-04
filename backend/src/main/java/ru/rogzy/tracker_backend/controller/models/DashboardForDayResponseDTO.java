package ru.rogzy.tracker_backend.controller.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardForDayResponseDTO {
    private List<FoodLogDTO> foodLog;
    private WeightLogForDayResponseDTO weight;
    private GoalLogForDayResponseDTO goal;
}
