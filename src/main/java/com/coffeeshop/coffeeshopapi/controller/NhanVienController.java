package com.coffeeshop.coffeeshopapi.controller;

import com.coffeeshop.coffeeshopapi.dto.NhanVienRequest;
import com.coffeeshop.coffeeshopapi.dto.NhanVienResponse;
import com.coffeeshop.coffeeshopapi.service.NhanVienService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhanvien")
@RequiredArgsConstructor
public class NhanVienController {

    private final NhanVienService service;

    @PostMapping
    public NhanVienResponse create(@RequestBody NhanVienRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<NhanVienResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public NhanVienResponse getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public NhanVienResponse update(
            @PathVariable String id,
            @RequestBody NhanVienRequest req
    ) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "Đã xóa nhân viên: " + id;
    }

    // 🔥 API TÌM KIẾM NHÂN VIÊN
    @GetMapping("/search")
    public ResponseEntity<List<NhanVienResponse>> searchNhanVien(@RequestParam String keyword) {
        return ResponseEntity.ok(service.search(keyword));  // SỬ DỤNG service !!!
    }
}
