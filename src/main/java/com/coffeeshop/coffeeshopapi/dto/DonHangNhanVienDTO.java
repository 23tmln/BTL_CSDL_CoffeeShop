package com.coffeeshop.coffeeshopapi.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonHangNhanVienDTO {
    private String maDonHang;
    private LocalDate ngayLap;
    private String tenKH;
    private BigDecimal tongTien;
}
