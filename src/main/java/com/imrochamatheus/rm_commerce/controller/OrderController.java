package com.imrochamatheus.rm_commerce.controller;

import com.imrochamatheus.rm_commerce.dto.OrderDTO;
import com.imrochamatheus.rm_commerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<OrderDTO> insertOrder (@Valid @RequestBody OrderDTO orderDTO) {
        orderDTO = orderService.insertOrder(orderDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(orderDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(orderDTO);
    }
}
