package com.imrochamatheus.rm_commerce.mapper;

import com.imrochamatheus.rm_commerce.dto.PaymentDTO;
import com.imrochamatheus.rm_commerce.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper extends BaseMapper<PaymentDTO, Payment> {

    public PaymentMapper () {
        super(PaymentDTO.class, Payment.class);
    }
}
