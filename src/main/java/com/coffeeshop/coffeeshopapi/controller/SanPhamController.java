package com.coffeeshop.coffeeshopapi.controller;

import com.coffeeshop.coffeeshopapi.entity.SanPham;
import com.coffeeshop.coffeeshopapi.repository.SanPhamRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sanpham")
public class SanPhamController {

    private final SanPhamRepository repo;

    public SanPhamController(SanPhamRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<SanPham> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public SanPham getOne(@PathVariable String id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public SanPham create(@RequestBody SanPham sp) {
        return repo.save(sp);
    }

    @PutMapping("/{id}")
    public SanPham update(@PathVariable String id, @RequestBody SanPham sp) {
        sp.setMaSP(id);
        return repo.save(sp);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        repo.deleteById(id);
    }
}
