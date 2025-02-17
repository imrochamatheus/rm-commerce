package com.imrochamatheus.rm_commerce.dto;

import com.imrochamatheus.rm_commerce.model.OrderStatus;
import com.imrochamatheus.rm_commerce.model.Payment;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class OrderDTO {
    private Long id;
    private Instant moment;
    private UserDTO client;
    private PaymentDTO payment;
    private OrderStatus status;

    @NotEmpty(message = "must have at least 1 item")
    private List<OrderItemDTO> items = new ArrayList<>();

    public Double getTotal () {
        return items.stream()
                .mapToDouble(orderItemDTO ->
                        orderItemDTO.getPrice() * orderItemDTO.getQuantity()).sum();
    }
}
