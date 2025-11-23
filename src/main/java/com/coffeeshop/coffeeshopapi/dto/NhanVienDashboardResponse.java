package com.coffeeshop.coffeeshopapi.dto.dashboard;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class NhanVienDashboardResponse {

    // Thông tin nhân viên
    private String maNV;
    private String tenNV;
    private String chucVu;
    private BigDecimal luong;
    private String trangThaiLamViec;

    // KPI tổng quan
    private long tongSoDon;
    private BigDecimal tongDoanhThu;
    private BigDecimal giaTriTrungBinhDon;
    private long soCaLam;

    // Chi tiết
    private List<CaLamNhanVienDTO> caLams;
    private List<KpiNgayNhanVienDTO> kpiTheoNgay;
    private List<DonHangNhanVienDTO> donHangs;
}
