package com.coffeeshop.coffeeshopapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "CaLam")
@Data
public class CaLam {

    @Id
    @Column(name = "MaCa")
    private String maCa;

    @Column(name = "Ca")
    private String ca; // Sang, Trua, Toi

    @Column(name = "NgayLam")
    private Date ngayLam;

    @Column(name = "MaNV")
    private String maNV;  // Nhân viên làm ca đó
}
