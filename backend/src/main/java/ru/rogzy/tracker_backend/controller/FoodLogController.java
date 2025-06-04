package ru.rogzy.tracker_backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rogzy.tracker_backend.controller.models.BaseError;
import ru.rogzy.tracker_backend.controller.models.BaseResponse;
import ru.rogzy.tracker_backend.controller.models.FoodLogDTO;
import ru.rogzy.tracker_backend.controller.models.FoodLogForDayRequestDTO;
import ru.rogzy.tracker_backend.repository.models.FoodLogDO;
import ru.rogzy.tracker_backend.service.AuthService;
import ru.rogzy.tracker_backend.service.FoodLogService;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@RestController
@RequestMapping("/v1/api/food_log")
@Validated
@AllArgsConstructor
public class FoodLogController {
    private FoodLogService foodLogService;
    private AuthService authService;

    @PostMapping
    public ResponseEntity<BaseResponse<FoodLogDTO>> create(
            @RequestBody FoodLogDTO requestDTO,
            Authentication authentication
    ) {
        var name = authentication.getName();
        var userId = authService.getUserByEmail(name);
        var foodLog = foodLogService.createOrUpdate(userId, requestDTO);
        return ResponseEntity.ok(BaseResponse.<FoodLogDTO>builder().data(foodLog).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<FoodLogDO>> getById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        var name = authentication.getName();
        var userId = authService.getUserByEmail(name);
        var log = foodLogService.findByUserIdAndId(userId, id);
        return log.map(foodLogDO -> ResponseEntity.ok(new BaseResponse<>(foodLogDO)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new BaseResponse<>(new BaseError("food log was not found", null, null)))
                );
    }

    @PostMapping("/day")
    public ResponseEntity<BaseResponse<List<FoodLogDTO>>> getDay(
            @RequestBody FoodLogForDayRequestDTO requestDTO,
            Authentication authentication
    ) {
        var name = authentication.getName();
        var userId = authService.getUserByEmail(name);
        try {
            var date = ZonedDateTime.parse(requestDTO.getDate());
            var from = date.toInstant().truncatedTo(ChronoUnit.DAYS);
            var to = from.plus(1, ChronoUnit.DAYS).minus(1, ChronoUnit.SECONDS);
            var allByPeriod = foodLogService.findAllByPeriod(userId, from, to);
            return ResponseEntity.ok(new BaseResponse<>(allByPeriod));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    BaseResponse.<List<FoodLogDTO>>builder()
                            .error(BaseError.builder()
                                    .message(e.getMessage())
                                    .build())
                            .build()
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> removeById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        var name = authentication.getName();
        var userId = authService.getUserByEmail(name);
        try {
            foodLogService.deleteByUserIdAndId(userId, id);
            return ResponseEntity.ok(BaseResponse.<String>builder().data("OK").build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    BaseResponse.<String>builder()
                            .error(
                                    BaseError.builder().message(e.getMessage()).build()
                            ).build()
            );
        }
    }
}
