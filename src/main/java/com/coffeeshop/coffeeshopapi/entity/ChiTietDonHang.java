package com.coffeeshop.coffeeshopapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ChiTietDonHang")
@IdClass(ChiTietDonHangId.class)
public class ChiTietDonHang {

    @Id
    @Column(name = "MaDonHang")
    private String maDonHang;

    @Id
    @Column(name = "MaSP")
    private String maSP;

    @Column(name = "SoLuong")
    private int soLuong;

    @Column(name = "GiaBanThucTe")
    private int giaBanThucTe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDonHang", insertable = false, updatable = false)
    private DonHang donHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSP", insertable = false, updatable = false)
    private SanPham sanPham;
}
