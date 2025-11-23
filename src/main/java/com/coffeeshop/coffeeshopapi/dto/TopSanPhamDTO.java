package com.coffeeshop.coffeeshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSanPhamDTO {
    private String maSP;
    private String tenSP;
    private long tongSL;
    private double doanhThu;
}
