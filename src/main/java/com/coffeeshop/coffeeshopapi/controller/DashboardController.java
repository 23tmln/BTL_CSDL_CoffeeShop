package com.coffeeshop.coffeeshopapi.controller;

import com.coffeeshop.coffeeshopapi.dto.DashboardDTO;
import com.coffeeshop.coffeeshopapi.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    // GET /api/dashboard?from=2025-01-01&to=2025-01-31
    @GetMapping
    public DashboardDTO getDashboard(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to
    ) {
        return dashboardService.getDashboard(from, to);
    }
}
