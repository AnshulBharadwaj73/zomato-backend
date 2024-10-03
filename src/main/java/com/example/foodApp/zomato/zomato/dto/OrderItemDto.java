package com.example.foodApp.zomato.zomato.dto;

import com.example.foodApp.zomato.zomato.entities.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

//    private Long id;
    private Long menuItemId;
//    private String menuItemName; // Optional: If you want to include the menu item name
    private Integer quantity;
//    private Double price;
//    private OrderStatus orderStatus;
}
