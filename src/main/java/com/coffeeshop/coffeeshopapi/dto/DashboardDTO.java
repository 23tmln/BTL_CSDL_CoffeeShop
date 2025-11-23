package com.coffeeshop.coffeeshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {

    // Thẻ thống kê tổng quan
    private long tongSoDonHang;
    private double tongDoanhThu;
    private double giaTriTrungBinhDonHang;

    // Biểu đồ doanh số theo nhân viên
    private List<DoanhThuNhanVienDTO> doanhThuTheoNhanVien;

    // Biểu đồ doanh số theo sản phẩm
    private List<DoanhThuSanPhamDTO> doanhThuTheoSanPham;

    // Bảng TOP sản phẩm bán chạy
    private List<TopSanPhamDTO> topSanPhamBanChay;
}
