package com.example.foodApp.zomato.zomato.services;

import com.example.foodApp.zomato.zomato.dto.OrderRequestDto;

public interface OrderRequestService {

    // createNewOrder, updateOrderById, deleteOrderById
    OrderRequestDto createNewOrderRequest(String restaurantName,OrderRequestDto orderRequestDto);
    OrderRequestDto confirmExistingOrder(Long id);
    void deleteOrderByOrderId(Long orderId);
//    OrderRequestDto updateOrderStatus(Long orderId, String status);
    OrderRequestDto confirmPaymentForOrderRequest(Long OrderRequestId);
    void deleteOrderWithOrderId(Long OrderId);

}
