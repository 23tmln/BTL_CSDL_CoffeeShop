package com.coffeeshop.coffeeshopapi.controller;

import com.coffeeshop.coffeeshopapi.dto.dashboard.NhanVienDashboardResponse;
import com.coffeeshop.coffeeshopapi.service.NhanVienDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/nhanvien-dashboard")
@RequiredArgsConstructor
public class NhanVienDashboardController {

    private final NhanVienDashboardService service;

    @GetMapping("/{maNV}")
    public NhanVienDashboardResponse dashboard(
            @PathVariable String maNV,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return service.getDashboard(maNV, from, to);
    }
}
