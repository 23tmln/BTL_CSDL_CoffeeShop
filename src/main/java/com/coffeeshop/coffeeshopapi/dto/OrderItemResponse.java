package com.coffeeshop.coffeeshopapi.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
    private String maSP;
    private String tenSP;
    private Integer soLuong;
    private Integer giaBanThucTe;
}
