package com.coffeeshop.coffeeshopapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "NhanVien")
@Data
public class NhanVien {

    @Id
    @Column(name = "MaNV")
    private String maNV;

    @Column(name = "TenNV")
    private String tenNV;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "GioiTinh")
    private String gioiTinh;

    @Column(name = "NgaySinh")
    private Date ngaySinh;

    @Column(name = "Luong")
    private Integer luong;

    @Column(name = "TrangThaiLamViec")
    private String trangThaiLamViec;

    @Column(name = "ChucVu")
    private String chucVu;
}
