package com.example.foodApp.zomato.zomato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

//    private CartDto cartDto;

    private MenuItemDto menuItemDto;

    private Integer quantity;
}
