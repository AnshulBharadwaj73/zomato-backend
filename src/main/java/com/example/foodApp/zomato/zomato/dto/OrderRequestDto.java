package com.example.foodApp.zomato.zomato.dto;

import com.example.foodApp.zomato.zomato.entities.OrderItem;
import com.example.foodApp.zomato.zomato.entities.enums.OrderStatus;
import com.example.foodApp.zomato.zomato.entities.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private UserDto userDto;
    private RestaurantDto restaurantDto;
    private List<OrderItemDto> orderItems;
    private Double price;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private LocalDateTime updatedAt;
    private Double tip;
    private String specialInstruction;
}
