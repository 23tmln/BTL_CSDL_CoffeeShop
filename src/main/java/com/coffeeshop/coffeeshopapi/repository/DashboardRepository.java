package com.coffeeshop.coffeeshopapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class DashboardRepository {

    @PersistenceContext
    private EntityManager em;

    // ===========================
    // 1. Tổng quan: tổng đơn, tổng doanh thu, trung bình
    // ===========================
    public Object[] thongKeTongQuan(Date from, Date to) {
        String sql = """
            SELECT 
                COUNT(*) AS tongDon,
                SUM(TongTien) AS tongDoanhThu,
                AVG(TongTien) AS giaTriTB
            FROM DonHang
            WHERE NgayLap BETWEEN :from AND :to
        """;

        var query = em.createNativeQuery(sql);
        query.setParameter("from", from);
        query.setParameter("to", to);

        return (Object[]) query.getSingleResult();
    }

    // ===========================
    // 2. Doanh thu theo nhân viên
    // ===========================
    public List<Object[]> doanhThuTheoNhanVien(Date from, Date to) {
        String sql = """
            SELECT 
                nv.MaNV,
                nv.TenNV,
                SUM(dh.TongTien) AS doanhThu
            FROM DonHang dh
            JOIN NhanVien nv ON dh.MaNV = nv.MaNV
            WHERE dh.NgayLap BETWEEN :from AND :to
            GROUP BY nv.MaNV, nv.TenNV
            ORDER BY SUM(dh.TongTien) DESC
        """;

        var query = em.createNativeQuery(sql);
        query.setParameter("from", from);
        query.setParameter("to", to);

        return query.getResultList();
    }

    // ===========================
    // 3. Doanh thu theo sản phẩm
    // ===========================
    public List<Object[]> doanhThuTheoSanPham(Date from, Date to) {
        String sql = """
            SELECT 
                sp.MaSP,
                sp.TenSP,
                SUM(ct.SoLuong * ct.GiaBanThucTe) AS doanhThu
            FROM ChiTietDonHang ct
            JOIN DonHang dh ON ct.MaDonHang = dh.MaDonHang
            JOIN SanPham sp ON ct.MaSP = sp.MaSP
            WHERE dh.NgayLap BETWEEN :from AND :to
            GROUP BY sp.MaSP, sp.TenSP
            ORDER BY SUM(ct.SoLuong * ct.GiaBanThucTe) DESC
        """;

        var query = em.createNativeQuery(sql);
        query.setParameter("from", from);
        query.setParameter("to", to);

        return query.getResultList();
    }

    // ===========================
    // 4. TOP 5 sản phẩm bán chạy
    // ===========================
    public List<Object[]> topSanPhamBanChay(Date from, Date to) {
        String sql = """
            SELECT TOP 5
                sp.MaSP,
                sp.TenSP,
                SUM(ct.SoLuong) AS tongSL,
                SUM(ct.SoLuong * ct.GiaBanThucTe) AS doanhThu
            FROM ChiTietDonHang ct
            JOIN DonHang dh ON ct.MaDonHang = dh.MaDonHang
            JOIN SanPham sp ON ct.MaSP = sp.MaSP
            WHERE dh.NgayLap BETWEEN :from AND :to
            GROUP BY sp.MaSP, sp.TenSP
            ORDER BY SUM(ct.SoLuong) DESC
        """;

        var query = em.createNativeQuery(sql);
        query.setParameter("from", from);
        query.setParameter("to", to);

        return query.getResultList();
    }
}
