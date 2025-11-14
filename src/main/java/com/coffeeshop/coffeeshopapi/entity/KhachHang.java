package com.coffeeshop.coffeeshopapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "KhachHang")
@Data
public class KhachHang {

    @Id
    @Column(name = "MaKH")
    private String maKH;

    @Column(name = "TenKH")
    private String tenKH;

    @Column(name = "SDT")
    private String sdt;
}
