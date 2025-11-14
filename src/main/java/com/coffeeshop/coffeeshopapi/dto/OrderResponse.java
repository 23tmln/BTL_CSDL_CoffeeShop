package com.coffeeshop.coffeeshopapi.dto;

import lombok.Data;
import java.sql.Date;
import java.util.List;

@Data
public class OrderResponse {

    private String maDonHang;
    private Date ngayLap;
    private Integer tongTien;

    private String maKH;
    private String tenKH;

    private String maNV;
    private String tenNV;

    private List<OrderItemResponse> items;
}
