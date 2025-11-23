package com.coffeeshop.coffeeshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoanhThuSanPhamDTO {
    private String maSP;
    private String tenSP;
    private double doanhThu;
}
