package com.coffeeshop.coffeeshopapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ChiTietDonHangId implements Serializable {

    @Column(name = "MaDonHang")
    private String maDonHang;

    @Column(name = "MaSP")
    private String maSP;

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }
}
