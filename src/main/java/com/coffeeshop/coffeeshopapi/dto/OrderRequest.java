package com.coffeeshop.coffeeshopapi.dto;

import lombok.Data;
import java.sql.Date;
import java.util.List;

@Data
public class OrderRequest {
    private String maKH;
    private List<OrderItemRequest> items;
}
