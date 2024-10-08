package com.example.foodApp.zomato.zomato.dto;

import com.example.foodApp.zomato.zomato.entities.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseDto {

    private OrderRequestDto orderRequestDto;
    private DeliveryBoyDto deliveryBoyDto;
    private LocalDateTime pickupTime;
    private PointDto pickupLocation;
    private PointDto dropOffLocation;
    private DeliveryStatus deliveryStatus;
}
