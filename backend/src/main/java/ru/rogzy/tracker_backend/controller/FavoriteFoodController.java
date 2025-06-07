package ru.rogzy.tracker_backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rogzy.tracker_backend.controller.models.BaseError;
import ru.rogzy.tracker_backend.controller.models.BaseResponse;
import ru.rogzy.tracker_backend.controller.models.FavoriteFoodDTO;
import ru.rogzy.tracker_backend.service.AuthService;
import ru.rogzy.tracker_backend.service.FavoriteFoodService;

@RestController
@RequestMapping("/v1/api/favorite_food")
@Validated
@AllArgsConstructor
public class FavoriteFoodController {
    private FavoriteFoodService favoriteFoodService;
    private AuthService authService;

    @PostMapping
    public ResponseEntity<BaseResponse<FavoriteFoodDTO>> create(
            @RequestBody FavoriteFoodDTO requestDTO,
            Authentication authentication
    ) {
        var name = authentication.getName();
        var userId = authService.getUserByEmail(name);
        var foodLog = favoriteFoodService.createOrUpdate(userId, requestDTO);
        return ResponseEntity.ok(BaseResponse.<FavoriteFoodDTO>builder().data(foodLog).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<FavoriteFoodDTO>> getById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        var name = authentication.getName();
        var userId = authService.getUserByEmail(name);
        var fav = favoriteFoodService.findByUserIdAndId(userId, id);
        return fav.map(foodLogDO -> ResponseEntity.ok(new BaseResponse<>(foodLogDO)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new BaseResponse<>(new BaseError("food log was not found", null, null)))
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> removeById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        var name = authentication.getName();
        var userId = authService.getUserByEmail(name);
        try {
            favoriteFoodService.deleteByUserIdAndId(userId, id);
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
