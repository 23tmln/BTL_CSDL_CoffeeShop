package com.coffeeshop.coffeeshopapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CongThuc")
@IdClass(CongThucId.class)
@Data
public class CongThuc {

    @Id
    @Column(name = "MaSP")
    private String maSP;

    @Id
    @Column(name = "MaNL")
    private String maNL;

    @Column(name = "SoLuongCanDung")
    private Integer soLuongCanDung;

    @ManyToOne
    @JoinColumn(name = "MaSP", insertable = false, updatable = false)
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "MaNL", insertable = false, updatable = false)
    private NguyenLieu nguyenLieu;
}
