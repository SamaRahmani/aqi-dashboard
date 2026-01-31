package com.aqi.aqi_calculator.controller;

import com.aqi.aqi_calculator.service.AqiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AqiController {

    private final AqiService service;

    public AqiController(AqiService service) {
        this.service = service;
    }

    @GetMapping("/api/dashboard")
    public Object getDashboard(@RequestParam String city) {
        return service.getDashboard(city);
    }
}
