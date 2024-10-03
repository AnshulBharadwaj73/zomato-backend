package com.example.foodApp.zomato.zomato.dto;

import com.example.foodApp.zomato.zomato.entities.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private OrderRequestDto orderRequestDto;

    private Double amount;

    private Double deliveryFee;

    private Double tip;

    private Double tax_GST;

    private PaymentMethod paymentMethod;

    private Boolean isPaid;
}
