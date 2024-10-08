package com.example.foodApp.zomato.zomato.strategies;

import com.example.foodApp.zomato.zomato.entities.enums.PaymentMethod;
import com.example.foodApp.zomato.zomato.strategies.impl.CODDiscountStrategyImpl;
import com.example.foodApp.zomato.zomato.strategies.impl.UPIDiscountStrategiesImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentStrategyManager {

    private final UPIDiscountStrategiesImpl upiDiscountStrategy;
    private final CODDiscountStrategyImpl codDiscountStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch (paymentMethod) {
            case CREDIT -> null;
            case DEBIT -> null;
            case UPI -> upiDiscountStrategy;
            case COD -> codDiscountStrategy;
            default -> throw new IllegalArgumentException("Unknown payment method: " + paymentMethod);
        };
    }
}
