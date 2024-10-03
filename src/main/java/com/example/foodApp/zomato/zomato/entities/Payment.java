package com.example.foodApp.zomato.zomato.entities;

import com.example.foodApp.zomato.zomato.entities.enums.PaymentMethod;
import com.example.foodApp.zomato.zomato.entities.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_request_id", nullable = false)
    private OrderRequest orderRequest;

    private Double amount;

    private Double deliveryFee;

    private Double tip;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Boolean isPaid;
}
