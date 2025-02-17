package com.imrochamatheus.rm_commerce.repository;

import com.imrochamatheus.rm_commerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
