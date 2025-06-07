package ru.rogzy.tracker_backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rogzy.tracker_backend.controller.models.*;
import ru.rogzy.tracker_backend.converters.WeightLogMapper;
import ru.rogzy.tracker_backend.security.UserPrincipal;
import ru.rogzy.tracker_backend.service.AuthService;
import ru.rogzy.tracker_backend.service.WeightLogService;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/v1/api/weight_log")
@Validated
@AllArgsConstructor
public class WeightLogController {
    private WeightLogService weightLogService;
    private AuthService authService;

    @PostMapping
    public ResponseEntity<BaseResponse<WeightLogDTO>> create(
            @RequestBody WeightLogDTO requestDTO,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        var userId = user.getUserId();
        var weightLog = weightLogService.create(userId, requestDTO);
        return ResponseEntity.ok(BaseResponse.<WeightLogDTO>builder().data(weightLog).build());
    }

    @PostMapping("/day")
    public ResponseEntity<BaseResponse<WeightLogForDayResponseDTO>> getDay(
            @RequestBody WeightLogForDayRequestDTO requestDTO,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        var userId = user.getUserId();
        try {
            var date = ZonedDateTime.parse(requestDTO.getDate());
            var from = date.toInstant().truncatedTo(ChronoUnit.DAYS);
            var to = from.plus(1, ChronoUnit.DAYS).minus(1, ChronoUnit.SECONDS);
            var response = weightLogService.getDay(userId, from, to);
            return ResponseEntity.ok(new BaseResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    BaseResponse.<WeightLogForDayResponseDTO>builder()
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
            weightLogService.deleteByUserIdAndId(userId, id);
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
