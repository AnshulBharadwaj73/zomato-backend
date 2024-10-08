package com.example.foodApp.zomato.zomato.dto;

import com.example.foodApp.zomato.zomato.entities.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestDto {


    private PointDto pickupLocation;
    private PointDto dropOffLocation;

}
