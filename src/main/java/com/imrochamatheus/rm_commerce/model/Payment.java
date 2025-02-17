package com.imrochamatheus.rm_commerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "tb_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant moment;

    @OneToOne()
    @MapsId
    @JoinColumn(name = "order_id")
    private Order order;
    
    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", moment=" + moment +
                '}';
    }
}
