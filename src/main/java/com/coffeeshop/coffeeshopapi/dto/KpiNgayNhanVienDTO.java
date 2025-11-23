package com.coffeeshop.coffeeshopapi.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KpiNgayNhanVienDTO {
    private LocalDate ngay;
    private long soDon;
    private BigDecimal doanhThu;
}
