package com.coffeeshop.coffeeshopapi.service;

import com.coffeeshop.coffeeshopapi.dto.dashboard.*;
import com.coffeeshop.coffeeshopapi.entity.NhanVien;
import com.coffeeshop.coffeeshopapi.repository.NhanVienDashboardRepository;
import com.coffeeshop.coffeeshopapi.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NhanVienDashboardService {

    private final NhanVienDashboardRepository repo;
    private final NhanVienRepository nhanVienRepo;

    public NhanVienDashboardResponse getDashboard(
            String maNV,
            LocalDate from,
            LocalDate to
    ) {
        NhanVien nv = nhanVienRepo.findById(maNV)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên: " + maNV));

        Date fromDate = Date.valueOf(from);
        Date toDate   = Date.valueOf(to);

        // =============================
        // 1. TỔNG QUAN
        // =============================
        Object[] tongQuan = repo.tongQuanDonHang(maNV, fromDate, toDate);

        long tongSoDon = tongQuan[0] == null
                ? 0
                : ((Number) tongQuan[0]).longValue();

        BigDecimal tongDoanhThu = BigDecimal.ZERO;
        if (tongQuan[1] != null) {
            tongDoanhThu = BigDecimal.valueOf(((Number) tongQuan[1]).doubleValue());
        }

        BigDecimal giaTriTB = tongSoDon == 0
                ? BigDecimal.ZERO
                : tongDoanhThu.divide(BigDecimal.valueOf(tongSoDon), 0, RoundingMode.HALF_UP);

        // =============================
        // 2. KPI THEO NGÀY
        // =============================
        List<KpiNgayNhanVienDTO> kpiTheoNgay =
                repo.kpiTheoNgay(maNV, fromDate, toDate)
                        .stream()
                        .map(row -> new KpiNgayNhanVienDTO(
                                ((Date) row[0]).toLocalDate(),
                                row[1] == null ? 0 : ((Number) row[1]).longValue(),
                                row[2] == null
                                        ? BigDecimal.ZERO
                                        : BigDecimal.valueOf(((Number) row[2]).doubleValue())
                        ))
                        .collect(Collectors.toList());

        // =============================
        // 3. LỊCH SỬ ĐƠN HÀNG NHÂN VIÊN XỬ LÝ
        // =============================
        List<DonHangNhanVienDTO> donHangs =
                repo.donHangNhanVien(maNV, fromDate, toDate)
                        .stream()
                        .map(row -> new DonHangNhanVienDTO(
                                (String) row[0],
                                ((Date) row[1]).toLocalDate(),
                                (String) row[2],
                                row[3] == null
                                        ? BigDecimal.ZERO
                                        : BigDecimal.valueOf(((Number) row[3]).doubleValue())
                        ))
                        .collect(Collectors.toList());

        // =============================
        // 4. CA LÀM VIỆC
        // =============================
        List<CaLamNhanVienDTO> caLams =
                repo.caLamNhanVien(maNV, fromDate, toDate)
                        .stream()
                        .map(row -> new CaLamNhanVienDTO(
                                ((java.sql.Date) row[0]).toLocalDate(),
                                String.valueOf(row[1]),
                                "Không có"             // vì không có trạng thái
                        ))
                        .collect(Collectors.toList());

        long soCaLam = caLams.size();

        // =============================
        // BUILD RESPONSE
        // =============================
        return NhanVienDashboardResponse.builder()
                .maNV(nv.getMaNV())
                .tenNV(nv.getTenNV())
                .chucVu(nv.getChucVu())
                .luong(BigDecimal.valueOf(nv.getLuong()))

                .trangThaiLamViec(nv.getTrangThaiLamViec())

                .tongSoDon(tongSoDon)
                .tongDoanhThu(tongDoanhThu)
                .giaTriTrungBinhDon(giaTriTB)
                .soCaLam(soCaLam)

                .caLams(caLams)
                .kpiTheoNgay(kpiTheoNgay)
                .donHangs(donHangs)
                .build();
    }
}
