package com.imrochamatheus.rm_commerce.repository;

import com.imrochamatheus.rm_commerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
