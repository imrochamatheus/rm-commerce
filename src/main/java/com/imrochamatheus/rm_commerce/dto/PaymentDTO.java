package com.imrochamatheus.rm_commerce.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PaymentDTO {
    private Long id;
    private Instant moment;
}
