package ru.rogzy.tracker_backend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rogzy.tracker_backend.controller.models.RestoreAccountRequestDTO;
import ru.rogzy.tracker_backend.security.UserPrincipal;
import ru.rogzy.tracker_backend.service.impl.AccountServiceImpl;

@RestController
@RequestMapping("/v1/api/account")
@Validated
@AllArgsConstructor
public class AccountController {

    private AccountServiceImpl accountService;

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@AuthenticationPrincipal UserPrincipal user) {
        var userId = user.getUserId();
        accountService.delete(userId);
        return ResponseEntity.ok("Аккаунт удалён");
    }

    @PostMapping("/restore")
    public ResponseEntity<String> restore(@Valid @RequestBody RestoreAccountRequestDTO requestDTO) {
        accountService.restore(requestDTO.getId());
        return ResponseEntity.ok("Аккаунт восстановлен");
    }

}
