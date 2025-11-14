package com.coffeeshop.coffeeshopapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "DonHang")
public class DonHang {

    @Id
    @Column(name = "MaDonHang")
    private String maDonHang;

    @Column(name = "NgayLap")
    private Date ngayLap;

    @Column(name = "TongTien")
    private int tongTien;

    @ManyToOne
    @JoinColumn(name = "MaKH")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "MaNV")
    private NhanVien nhanVien;

    @OneToMany(
            mappedBy = "donHang",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ChiTietDonHang> chiTiet = new ArrayList<>();

}
