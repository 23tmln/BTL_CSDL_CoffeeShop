package com.coffeeshop.coffeeshopapi.service;

import com.coffeeshop.coffeeshopapi.dto.DashboardDTO;
import com.coffeeshop.coffeeshopapi.dto.DoanhThuNhanVienDTO;
import com.coffeeshop.coffeeshopapi.dto.DoanhThuSanPhamDTO;
import com.coffeeshop.coffeeshopapi.dto.TopSanPhamDTO;
import com.coffeeshop.coffeeshopapi.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardRepository repo;

    public DashboardDTO getDashboard(Date from, Date to) {

        // ===== 1. Thống kê tổng quan =====
        Object[] tongQuan = repo.thongKeTongQuan(from, to);
        long tongDon = 0;
        double tongDoanhThu = 0;
        double giaTriTB = 0;

        if (tongQuan != null) {
            if (tongQuan[0] != null) tongDon = ((Number) tongQuan[0]).longValue();
            if (tongQuan[1] != null) tongDoanhThu = ((Number) tongQuan[1]).doubleValue();
            if (tongQuan[2] != null) giaTriTB = ((Number) tongQuan[2]).doubleValue();
        }

        // ===== 2. Doanh thu theo nhân viên =====
        List<DoanhThuNhanVienDTO> dsNV = repo.doanhThuTheoNhanVien(from, to)
                .stream()
                .map(o -> new DoanhThuNhanVienDTO(
                        (String) o[0],
                        (String) o[1],
                        o[2] != null ? ((Number) o[2]).doubleValue() : 0
                ))
                .toList();

        // ===== 3. Doanh thu theo sản phẩm =====
        List<DoanhThuSanPhamDTO> dsSP = repo.doanhThuTheoSanPham(from, to)
                .stream()
                .map(o -> new DoanhThuSanPhamDTO(
                        (String) o[0],
                        (String) o[1],
                        o[2] != null ? ((Number) o[2]).doubleValue() : 0
                ))
                .toList();

        // ===== 4. TOP sản phẩm bán chạy =====
        List<TopSanPhamDTO> topSP = repo.topSanPhamBanChay(from, to)
                .stream()
                .map(o -> new TopSanPhamDTO(
                        (String) o[0],
                        (String) o[1],
                        o[2] != null ? ((Number) o[2]).longValue() : 0,
                        o[3] != null ? ((Number) o[3]).doubleValue() : 0
                ))
                .toList();

        // ===== 5. Build DTO tổng =====
        return DashboardDTO.builder()
                .tongSoDonHang(tongDon)
                .tongDoanhThu(tongDoanhThu)
                .giaTriTrungBinhDonHang(giaTriTB)
                .doanhThuTheoNhanVien(dsNV)
                .doanhThuTheoSanPham(dsSP)
                .topSanPhamBanChay(topSP)
                .build();
    }
}
