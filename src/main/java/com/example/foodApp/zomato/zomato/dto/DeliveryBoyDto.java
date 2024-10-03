package com.example.foodApp.zomato.zomato.dto;

import com.example.foodApp.zomato.zomato.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryBoyDto {

    private UserDto user;

    private String vehicleId;

//    private String name;

    private Boolean isAvailable;

    private AddressDto address;

    private PointDto currentLocation;
}
