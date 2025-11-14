package com.coffeeshop.coffeeshopapi.service;

import com.coffeeshop.coffeeshopapi.dto.*;
import com.coffeeshop.coffeeshopapi.entity.*;
import com.coffeeshop.coffeeshopapi.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DonHangService {

    private final DonHangRepository donHangRepo;
    private final KhachHangRepository khRepo;
    private final SanPhamRepository sanPhamRepo;
    private final CongThucRepository congThucRepo;
    private final NguyenLieuRepository nlRepo;
    private final ChiTietDonHangRepository ctRepo;
    private final CaLamRepository caLamRepo;

    @PersistenceContext
    private EntityManager em;

    public DonHangService(
            DonHangRepository donHangRepo,
            KhachHangRepository khRepo,
            SanPhamRepository sanPhamRepo,
            CongThucRepository congThucRepo,
            NguyenLieuRepository nlRepo,
            ChiTietDonHangRepository ctRepo,
            CaLamRepository caLamRepo
    ) {
        this.donHangRepo = donHangRepo;
        this.khRepo = khRepo;
        this.sanPhamRepo = sanPhamRepo;
        this.congThucRepo = congThucRepo;
        this.nlRepo = nlRepo;
        this.ctRepo = ctRepo;
        this.caLamRepo = caLamRepo;
    }

    // ===========================================================
    // 1. Tạo mã đơn hàng tự động
    // ===========================================================
    private String generateOrderId() {
        String maxId = donHangRepo.findMaxId();   // ví dụ trả về DH005
        if (maxId == null) return "DH001";
        int num = Integer.parseInt(maxId.substring(2)) + 1;
        return String.format("DH%03d", num);
    }

    // ===========================================================
    // 2. Gán nhân viên theo ca hiện tại
    // ===========================================================
    private String autoSelectNhanVien() {

        int hour = LocalDateTime.now().getHour();

        String ca =
                (hour >= 7 && hour < 12) ? "Sáng" :
                        (hour >= 12 && hour < 17) ? "Chiều" :
                                "Tối";

        Date today = Date.valueOf(LocalDate.now());

        String maNV = caLamRepo.findNhanVienByCaAndNgay(ca, today);

        if (maNV == null) {
            throw new RuntimeException("Không có nhân viên trực ca: " + ca);
        }

        return maNV;
    }

    // ===========================================================
    // 3. Trừ nguyên liệu
    // ===========================================================
    private void subtractNguyenLieu(String maSP, int soLuongSP) {

        List<CongThuc> congThucs = congThucRepo.findByMaSP(maSP);

        for (CongThuc ct : congThucs) {

            NguyenLieu nl = nlRepo.findById(ct.getMaNL())
                    .orElseThrow(() -> new RuntimeException("Không tìm NL: " + ct.getMaNL()));

            int soLuongTru = ct.getSoLuongCanDung() * soLuongSP;

            if (nl.getSoLuong() < soLuongTru) {
                throw new RuntimeException("Không đủ nguyên liệu: " + nl.getTenNL());
            }

            nl.setSoLuong(nl.getSoLuong() - soLuongTru);
            nlRepo.save(nl);
        }
    }

    // ===========================================================
    // 4. Hoàn nguyên nguyên liệu
    // ===========================================================
    private void refundNguyenLieu(String maSP, int soLuongSP) {

        List<CongThuc> congThucs = congThucRepo.findByMaSP(maSP);

        for (CongThuc ct : congThucs) {

            NguyenLieu nl = nlRepo.findById(ct.getMaNL())
                    .orElseThrow(() -> new RuntimeException("Không tìm NL: " + ct.getMaNL()));

            int soLuongCong = ct.getSoLuongCanDung() * soLuongSP;

            nl.setSoLuong(nl.getSoLuong() + soLuongCong);
            nlRepo.save(nl);
        }
    }

    // ===========================================================
    // 5. CREATE đơn hàng
    // ===========================================================
    public OrderResponse create(OrderRequest req) {

        String maDH = generateOrderId();

        DonHang don = new DonHang();
        don.setMaDonHang(maDH);
        don.setNgayLap(Date.valueOf(LocalDate.now()));

        // Nhân viên theo ca
        NhanVien nv = new NhanVien();
        nv.setMaNV(autoSelectNhanVien());
        don.setNhanVien(nv);

        // Khách hàng
        KhachHang kh = khRepo.findById(req.getMaKH())
                .orElseThrow(() -> new RuntimeException("Không tìm KH: " + req.getMaKH()));
        don.setKhachHang(kh);

        int tongTien = 0;

        donHangRepo.save(don);

        for (OrderItemRequest item : req.getItems()) {

            SanPham sp = sanPhamRepo.findById(item.getMaSP())
                    .orElseThrow(() -> new RuntimeException("Không tìm SP: " + item.getMaSP()));

            ChiTietDonHang ct = new ChiTietDonHang();

            ct.setMaDonHang(maDH);             // composite key
            ct.setMaSP(sp.getMaSP());
            ct.setSoLuong(item.getSoLuong());
            ct.setGiaBanThucTe(sp.getGiaBan());

            ct.setDonHang(don);
            ct.setSanPham(sp);

            don.getChiTiet().add(ct);
            ctRepo.save(ct);

            tongTien += sp.getGiaBan() * item.getSoLuong();
            subtractNguyenLieu(sp.getMaSP(), item.getSoLuong());
        }

        don.setTongTien(tongTien);
        DonHang saved = donHangRepo.save(don);

        return convertToDto(saved);
    }

    // ===========================================================
    // 6. UPDATE đơn hàng
    // ===========================================================
    public OrderResponse update(String id, UpdateOrderRequest req) {

        DonHang dh = donHangRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm đơn hàng: " + id));

        // Hoàn nguyên nguyên liệu từ chi tiết cũ
        for (ChiTietDonHang old : dh.getChiTiet()) {
            refundNguyenLieu(old.getMaSP(), old.getSoLuong());
        }

        // Xóa chi tiết cũ
        dh.getChiTiet().clear();
        ctRepo.deleteByMaDonHang(id);

        // Cập nhật khách hàng
        KhachHang kh = khRepo.findById(req.getMaKH())
                .orElseThrow(() -> new RuntimeException("Không tìm KH: " + req.getMaKH()));
        dh.setKhachHang(kh);

        int tongTien = 0;

        // Thêm chi tiết mới
        for (OrderItemRequest item : req.getItems()) {

            SanPham sp = sanPhamRepo.findById(item.getMaSP())
                    .orElseThrow(() -> new RuntimeException("Không tìm SP: " + item.getMaSP()));

            ChiTietDonHang ct = new ChiTietDonHang();

            ct.setMaDonHang(id);
            ct.setMaSP(item.getMaSP());
            ct.setSoLuong(item.getSoLuong());
            ct.setGiaBanThucTe(sp.getGiaBan());

            ct.setDonHang(dh);
            ct.setSanPham(sp);

            dh.getChiTiet().add(ct);

            tongTien += sp.getGiaBan() * item.getSoLuong();
            subtractNguyenLieu(item.getMaSP(), item.getSoLuong());
        }

        dh.setTongTien(tongTien);

        DonHang saved = donHangRepo.save(dh);

        return convertToDto(saved);
    }

    // ===========================================================
    // 7. DELETE đơn hàng
    // ===========================================================
    public void delete(String id) {

        DonHang dh = em.find(DonHang.class, id);
        if (dh == null) {
            throw new RuntimeException("Không tìm đơn hàng: " + id);
        }

        // Hoàn nguyên nguyên liệu
        for (ChiTietDonHang ct : dh.getChiTiet()) {
            refundNguyenLieu(ct.getMaSP(), ct.getSoLuong());
        }

        // Xóa chi tiết
        ctRepo.deleteByMaDonHang(id);

        // Detach để tránh dirty-check
        em.detach(dh);

        // Xóa đơn hàng
        em.createNativeQuery("DELETE FROM DonHang WHERE MaDonHang = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    // ===========================================================
    // 8. GET ALL – danh sách đơn hàng (tìm kiếm tất cả)
    // ===========================================================
    public List<OrderResponse> getAll() {
        return donHangRepo.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    // ===========================================================
    // 9. GET BY ID – tìm kiếm đơn hàng theo mã
    // ===========================================================
    public OrderResponse getById(String id) {
        DonHang don = donHangRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm đơn hàng: " + id));
        return convertToDto(don);
    }

    // ===========================================================
    // 10. Convert Entity → DTO
    // ===========================================================
    private OrderResponse convertToDto(DonHang don) {

        OrderResponse dto = new OrderResponse();

        dto.setMaDonHang(don.getMaDonHang());
        dto.setNgayLap(don.getNgayLap());
        dto.setTongTien(don.getTongTien());

        // Khách hàng
        dto.setMaKH(don.getKhachHang().getMaKH());
        dto.setTenKH(don.getKhachHang().getTenKH());

        // Nhân viên
        dto.setMaNV(don.getNhanVien().getMaNV());
        dto.setTenNV(don.getNhanVien().getTenNV());

        // Chi tiết đơn
        List<OrderItemResponse> items = don.getChiTiet().stream().map(ct -> {
            OrderItemResponse it = new OrderItemResponse();

            it.setMaSP(ct.getMaSP());
            // lấy tên sản phẩm qua quan hệ ManyToOne
            if (ct.getSanPham() != null) {
                it.setTenSP(ct.getSanPham().getTenSP());
            } else {
                // fallback nếu quan hệ chưa load
                sanPhamRepo.findById(ct.getMaSP())
                        .ifPresent(sp -> it.setTenSP(sp.getTenSP()));
            }

            it.setSoLuong(ct.getSoLuong());
            it.setGiaBanThucTe(ct.getGiaBanThucTe());

            return it;
        }).toList();

        dto.setItems(items);

        return dto;
    }
}
