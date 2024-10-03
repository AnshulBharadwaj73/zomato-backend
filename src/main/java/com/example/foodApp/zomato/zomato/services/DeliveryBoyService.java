package com.example.foodApp.zomato.zomato.services;

import com.example.foodApp.zomato.zomato.dto.DeliveryBoyDto;
import com.example.foodApp.zomato.zomato.dto.DeliveryBoyRequestDto;
import com.example.foodApp.zomato.zomato.dto.DeliveryRequestDto;
import com.example.foodApp.zomato.zomato.entities.DeliveryBoy;

public interface DeliveryBoyService {

    DeliveryBoyDto createDeliveryBoy(DeliveryBoyRequestDto deliveryBoyRequestDto);

    DeliveryBoyDto endDelivery(DeliveryBoyDto deliveryBoyDto);
}
