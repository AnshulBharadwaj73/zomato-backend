package com.example.foodApp.zomato.zomato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryBoyRequestDto {

    private String vehicleNumber;
    private PointDto currentLocation;
    private Double rating;
    private AddressDto address;
}
