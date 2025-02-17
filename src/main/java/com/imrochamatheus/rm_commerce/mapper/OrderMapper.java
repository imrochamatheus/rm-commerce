package com.imrochamatheus.rm_commerce.mapper;

import com.imrochamatheus.rm_commerce.dto.OrderDTO;
import com.imrochamatheus.rm_commerce.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper extends BaseMapper<OrderDTO, Order> {
    public OrderMapper() {
        super(OrderDTO.class, Order.class);
    }
}
