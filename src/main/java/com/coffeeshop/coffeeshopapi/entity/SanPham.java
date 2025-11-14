package com.coffeeshop.coffeeshopapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "SanPham")
@Data
public class SanPham {

    @Id
    @Column(name = "MaSP")
    private String maSP;

    @Column(name = "TenSP")
    private String tenSP;

    @Column(name = "LoaiDoUong")
    private String loaiDoUong;

    @Column(name = "GiaCost")
    private Integer giaCost;

    @Column(name = "GiaBan")
    private Integer giaBan;

}
