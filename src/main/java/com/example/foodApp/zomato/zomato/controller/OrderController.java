package com.example.foodApp.zomato.zomato.controller;


import com.example.foodApp.zomato.zomato.advices.ApiResponse;
import com.example.foodApp.zomato.zomato.dto.OrderRequestDto;
import com.example.foodApp.zomato.zomato.services.OrderRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/order")
@Tag(name = "Order APIs")
public class OrderController {

    public final OrderRequestService orderRequestService;

    @PostMapping("/createNewOrder/{name}")
    public ResponseEntity<OrderRequestDto> createNewOrder(@PathVariable String name, @RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.ok(orderRequestService.createNewOrderRequest(name,orderRequestDto));
    }

    @PostMapping("/updatePayment/{orderId}")
    public ResponseEntity<OrderRequestDto> updateExistingOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(orderRequestService.confirmPaymentForOrderRequest(orderId));
    }

    @PutMapping("/updateOrder/{orderId}")
    public ResponseEntity<OrderRequestDto> updateExistingOrderStatus(@PathVariable Long orderId){
        return ResponseEntity.ok(orderRequestService.confirmExistingOrder(orderId));
    }

    @DeleteMapping("{orderId}")
    public ResponseEntity<ApiResponse<?>> deleteOrderByOrderId(@PathVariable Long orderId){
        orderRequestService.deleteOrderByOrderId(orderId);
        ApiResponse res = new ApiResponse<>();
        return ResponseEntity.ok(res);
    }
}
