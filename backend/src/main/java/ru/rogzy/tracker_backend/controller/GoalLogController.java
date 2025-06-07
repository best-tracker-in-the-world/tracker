package ru.rogzy.tracker_backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rogzy.tracker_backend.controller.models.*;
import ru.rogzy.tracker_backend.security.UserPrincipal;
import ru.rogzy.tracker_backend.service.AuthService;
import ru.rogzy.tracker_backend.service.GoalLogService;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/v1/api/goal_log")
@Validated
@AllArgsConstructor
public class GoalLogController {
    private GoalLogService goalLogService;
    private AuthService authService;

    @PostMapping
    public ResponseEntity<BaseResponse<GoalLogDTO>> create(
            @RequestBody GoalLogDTO requestDTO,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        var userId = user.getUserId();
        var goalLog = goalLogService.create(userId, requestDTO);
        return ResponseEntity.ok(BaseResponse.<GoalLogDTO>builder().data(goalLog).build());
    }

    @PostMapping("/day")
    public ResponseEntity<BaseResponse<GoalLogForDayResponseDTO>> getDay(
            @RequestBody GoalLogForDayRequestDTO requestDTO,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        var userId = user.getUserId();
        try {
            var date = ZonedDateTime.parse(requestDTO.getDate());
            var from = date.toInstant().truncatedTo(ChronoUnit.DAYS);
            var to = from.plus(1, ChronoUnit.DAYS).minus(1, ChronoUnit.SECONDS);
            var response = goalLogService.getDay(userId, from, to);
            return ResponseEntity.ok(new BaseResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    BaseResponse.<GoalLogForDayResponseDTO>builder()
                            .error(BaseError.builder()
                                    .message(e.getMessage())
                                    .build())
                            .build()
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        var userId = user.getUserId();
        try {
            goalLogService.deleteByUserIdAndId(userId, id);
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
