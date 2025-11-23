package com.coffeeshop.coffeeshopapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class NhanVienDashboardRepository {

    @PersistenceContext
    private EntityManager em;

    // Tổng đơn + tổng doanh thu
    public Object[] tongQuanDonHang(String maNV, Date from, Date to) {
        String sql = """
            SELECT 
                COUNT(*) AS tongSoDon,
                ISNULL(SUM(dh.TongTien), 0) AS tongDoanhThu
            FROM DonHang dh
            WHERE dh.MaNV = :maNV
              AND dh.NgayLap BETWEEN :from AND :to
        """;

        var q = em.createNativeQuery(sql);
        q.setParameter("maNV", maNV);
        q.setParameter("from", from);
        q.setParameter("to", to);

        Object single = q.getSingleResult();
        return (Object[]) single;
    }

    // KPI theo ngày
    public List<Object[]> kpiTheoNgay(String maNV, Date from, Date to) {
        String sql = """
            SELECT 
                CONVERT(date, dh.NgayLap) AS ngay,
                COUNT(*) AS soDon,
                SUM(dh.TongTien) AS doanhThu
            FROM DonHang dh
            WHERE dh.MaNV = :maNV
              AND dh.NgayLap BETWEEN :from AND :to
            GROUP BY CONVERT(date, dh.NgayLap)
            ORDER BY ngay
        """;

        var q = em.createNativeQuery(sql);
        q.setParameter("maNV", maNV);
        q.setParameter("from", from);
        q.setParameter("to", to);

        return q.getResultList();
    }

    // Lịch sử đơn hàng nhân viên xử lý
    public List<Object[]> donHangNhanVien(String maNV, Date from, Date to) {
        String sql = """
            SELECT
                dh.MaDonHang,
                dh.NgayLap,
                kh.TenKH,
                dh.TongTien
            FROM DonHang dh
            LEFT JOIN KhachHang kh ON kh.MaKH = dh.MaKH
            WHERE dh.MaNV = :maNV
              AND dh.NgayLap BETWEEN :from AND :to
            ORDER BY dh.NgayLap DESC, dh.MaDonHang DESC
        """;

        var q = em.createNativeQuery(sql);
        q.setParameter("maNV", maNV);
        q.setParameter("from", from);
        q.setParameter("to", to);

        return q.getResultList();
    }

    // Ca làm của nhân viên
    public List<Object[]> caLamNhanVien(String maNV, Date from, Date to) {
        String sql = """
            SELECT
                cl.NgayLam,
                cl.Ca
            FROM CaLam cl
            WHERE cl.MaNV = :maNV
              AND cl.NgayLam BETWEEN :from AND :to
            ORDER BY cl.NgayLam, cl.Ca
        """;


        var q = em.createNativeQuery(sql);
        q.setParameter("maNV", maNV);
        q.setParameter("from", from);
        q.setParameter("to", to);

        return q.getResultList();
    }
}
