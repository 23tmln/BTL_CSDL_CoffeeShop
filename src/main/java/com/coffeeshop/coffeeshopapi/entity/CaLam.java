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
    private String ca;

    @Column(name = "NgayLam")
    private Date ngayLam;

    @Column(name = "MaNV")
    private String maNV;
}
