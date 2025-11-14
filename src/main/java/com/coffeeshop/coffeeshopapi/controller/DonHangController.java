package com.coffeeshop.coffeeshopapi.controller;

import com.coffeeshop.coffeeshopapi.dto.OrderRequest;
import com.coffeeshop.coffeeshopapi.dto.OrderResponse;
import com.coffeeshop.coffeeshopapi.dto.UpdateOrderRequest;
import com.coffeeshop.coffeeshopapi.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donhang")
@RequiredArgsConstructor
public class DonHangController {

    private final DonHangService service;

    // ================= CREATE =================
    @PostMapping
    public OrderResponse create(@RequestBody OrderRequest req) {
        return service.create(req);
    }

    // ================= GET ALL =================
    @GetMapping
    public List<OrderResponse> getAll() {
        return service.getAll();
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable String id) {
        return service.getById(id);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "Đã xóa đơn hàng: " + id;
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public OrderResponse update(
            @PathVariable String id,
            @RequestBody UpdateOrderRequest req
    ) {
        return service.update(id, req);
    }

}
