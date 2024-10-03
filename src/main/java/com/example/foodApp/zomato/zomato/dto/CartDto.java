package com.example.foodApp.zomato.zomato.dto;

import com.example.foodApp.zomato.zomato.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private UserDto userDto;

//    private CartDto cartDto;
//
//    private MenuItemDto menuItemDto;
private Double totalPrice;

    private List<CartItemDto> items;

    private Double quantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
