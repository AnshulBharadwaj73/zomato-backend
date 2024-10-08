package com.example.foodApp.zomato.zomato.services;

import com.example.foodApp.zomato.zomato.dto.DeliveryRequestDto;
import com.example.foodApp.zomato.zomato.dto.DeliveryResponseDto;
import com.example.foodApp.zomato.zomato.dto.OtpDto;

public interface DeliveryService {

    DeliveryResponseDto acceptDeliveryByDeliveryBoy(Long orderRequestId, DeliveryRequestDto deliveryRequestDto);



    DeliveryRequestDto endDeliveryByDeliveryBoy(Long deliveryId, OtpDto otpDto);
}
