package com.coffeeshop.coffeeshopapi.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaLamNhanVienDTO {
    private LocalDate ngayLam;
    private String ca;          // Ca sáng / chiều / tối hoặc "Ca 1", "Ca 2"...
    private String trangThai;   // Đang làm / Đã xong / Nghỉ
}
