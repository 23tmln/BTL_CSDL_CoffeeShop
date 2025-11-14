package com.coffeeshop.coffeeshopapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "NguyenLieu")
@Data
public class NguyenLieu {

    @Id
    @Column(name = "MaNL")
    private String maNL;

    @Column(name = "TenNL")
    private String tenNL;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonViTinh")
    private String donViTinh;

    @Column(name = "TrangThai")
    private String trangThai;

    @Column(name = "GiaNhap")
    private Integer giaNhap;
}
