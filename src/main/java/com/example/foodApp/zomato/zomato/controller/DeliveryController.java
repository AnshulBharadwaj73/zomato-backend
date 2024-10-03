package com.example.foodApp.zomato.zomato.controller;

import com.example.foodApp.zomato.zomato.dto.DeliveryBoyDto;
import com.example.foodApp.zomato.zomato.dto.DeliveryBoyRequestDto;
import com.example.foodApp.zomato.zomato.dto.DeliveryRequestDto;
import com.example.foodApp.zomato.zomato.dto.OtpDto;
import com.example.foodApp.zomato.zomato.services.DeliveryBoyService;
import com.example.foodApp.zomato.zomato.services.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
@AllArgsConstructor
public class DeliveryController {

    private final DeliveryBoyService deliveryBoyService;
    private final DeliveryService deliveryService;


    @PostMapping("/deliveryBoy")
    public ResponseEntity<DeliveryBoyDto> createDeliveryBoy(@RequestBody DeliveryBoyRequestDto deliveryBoyRequestDto){
        return ResponseEntity.ok(deliveryBoyService.createDeliveryBoy(deliveryBoyRequestDto));
    }

    @PostMapping("/acceptDelivery/{requestId}")
    public ResponseEntity<DeliveryRequestDto> acceptDelivery(@PathVariable Long requestId,@RequestBody DeliveryRequestDto deliveryRequestDto){
        return ResponseEntity.ok(deliveryService.acceptDeliveryByDeliveryBoy(requestId,deliveryRequestDto));
    }

    @PostMapping("/endDelivery/{orderRequestId}")
    public ResponseEntity<DeliveryRequestDto> endDelivery(@PathVariable Long orderRequestId,@RequestBody OtpDto otpDto){
        return ResponseEntity.ok(deliveryService.endDeliveryByDeliveryBoy(orderRequestId,otpDto));
    }
}
