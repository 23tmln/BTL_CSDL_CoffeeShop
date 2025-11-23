package com.coffeeshop.coffeeshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoanhThuNhanVienDTO {
    private String maNV;
    private String tenNV;
    private double doanhThu;
}
