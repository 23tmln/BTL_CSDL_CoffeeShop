package com.coffeeshop.coffeeshopapi.dto;

import lombok.Data;
import java.sql.Date;

@Data
public class NhanVienRequest {
    private String tenNV;
    private String sdt;
    private String gioiTinh;
    private Date ngaySinh;
    private Integer luong;
    private String trangThaiLamViec;
    private String chucVu;
}
