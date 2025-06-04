package ru.rogzy.tracker_backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/dashboard")
@Validated
@AllArgsConstructor
public class DashboardController {
}
