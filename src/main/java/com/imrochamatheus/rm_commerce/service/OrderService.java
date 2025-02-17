package com.imrochamatheus.rm_commerce.service;

import com.imrochamatheus.rm_commerce.dto.OrderDTO;
import com.imrochamatheus.rm_commerce.dto.OrderItemDTO;
import com.imrochamatheus.rm_commerce.dto.PaymentDTO;
import com.imrochamatheus.rm_commerce.dto.UserDTO;
import com.imrochamatheus.rm_commerce.exception.NotFoundException;
import com.imrochamatheus.rm_commerce.mapper.OrderMapper;
import com.imrochamatheus.rm_commerce.mapper.PaymentMapper;
import com.imrochamatheus.rm_commerce.mapper.UserMapper;
import com.imrochamatheus.rm_commerce.model.*;
import com.imrochamatheus.rm_commerce.repository.OrderItemRepository;
import com.imrochamatheus.rm_commerce.repository.OrderRepository;
import com.imrochamatheus.rm_commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PaymentMapper paymentMapper;


    @Transactional()
    public OrderDTO findById (Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order with id " + orderId + " does not exists"));

        return orderMapper.toDTO(order);
    }

    @Transactional
    public OrderDTO insertOrder (OrderDTO orderDTO) {
        Order order = new Order();
        order.setClient(null);
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        for (OrderItemDTO orderItemDTO : orderDTO.getItems()) {
            Product product = productRepository.getReferenceById(orderItemDTO.getProductId());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(orderItemDTO.getQuantity());
            order.getItems().add(orderItem);
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());


//        User user = order.getClient();
//        UserDTO userDTO = userMapper.toDTO(user);

//        Payment payment = order.getPayment();
//        PaymentDTO paymentDTO = paymentMapper.toDTO(payment);

        orderDTO.setId(order.getId());
        orderDTO.setClient(null);
        orderDTO.setPayment(null);
        orderDTO.setMoment(order.getMoment());
        orderDTO.setStatus(order.getStatus());
        orderDTO.getItems().clear();

        order.getItems().stream().forEach(orderItem -> {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setQuantity(orderItem.getQuantity());
            orderItemDTO.setName(orderItem.getProduct().getName());
            orderItemDTO.setPrice(orderItem.getProduct().getPrice());
            orderItemDTO.setProductId(orderItem.getProduct().getId());
            orderItemDTO.setImgUrl(orderItem.getProduct().getImgUrl());

            orderDTO.getItems().add(orderItemDTO);
        });

        return orderDTO;
    }
}
