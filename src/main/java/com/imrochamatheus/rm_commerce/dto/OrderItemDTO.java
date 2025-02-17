package com.imrochamatheus.rm_commerce.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long productId;
    private String name;
    private Double price;
    private String imgUrl;
    private Integer quantity;

    public Double getSubtotal() {
        return quantity * price;
    }
}
