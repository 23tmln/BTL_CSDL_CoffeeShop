package com.coffeeshop.coffeeshopapi.dto;

import lombok.Data;
import java.util.List;

@Data
public class UpdateOrderRequest {
    private String maKH;
    private List<OrderItemRequest> items;
}
