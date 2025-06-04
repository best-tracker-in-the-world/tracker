package ru.rogzy.tracker_backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rogzy.tracker_backend.controller.models.*;
import ru.rogzy.tracker_backend.service.AuthService;
import ru.rogzy.tracker_backend.service.FoodLogService;
import ru.rogzy.tracker_backend.service.GoalLogService;
import ru.rogzy.tracker_backend.service.WeightLogService;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/v1/api/dashboard")
@Validated
@AllArgsConstructor
public class DashboardController {
    private final AuthService authService;
    private final FoodLogService foodLogService;
    private final WeightLogService weightLogService;
    private final GoalLogService goalLogService;

    @PostMapping("/day")
    public ResponseEntity<BaseResponse<DashboardForDayResponseDTO>> day(
            @RequestBody DashboardForDayRequestDTO requestDTO,
            Authentication authentication
    ) {
        var name = authentication.getName();
        var userId = authService.getUserByEmail(name);
        try {
            var date = ZonedDateTime.parse(requestDTO.getDate());
            var from = date.toInstant().truncatedTo(ChronoUnit.DAYS);
            var to = from.plus(1, ChronoUnit.DAYS).minus(1, ChronoUnit.SECONDS);
            var foodByPeriod = foodLogService.findAllByPeriod(userId, from, to);
            var weightByPeriod = weightLogService.getDay(userId, from, to);
            var goalByPeriod = goalLogService.getDay(userId, from, to);
            var response = DashboardForDayResponseDTO.builder()
                    .foodLog(foodByPeriod)
                    .weight(weightByPeriod)
                    .goal(goalByPeriod)
                    .build();
            return ResponseEntity.ok(new BaseResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    BaseResponse.<DashboardForDayResponseDTO>builder()
                            .error(BaseError.builder()
                                    .message(e.getMessage())
                                    .build())
                            .build()
            );
        }
    }

}
