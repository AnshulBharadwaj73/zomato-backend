package com.example.foodApp.zomato.zomato.services;

import com.example.foodApp.zomato.zomato.dto.MenuItemDto;

public interface MenuService {
    MenuItemDto createNewMenu(MenuItemDto menuItemDto);
    MenuItemDto updateExistingMenuPrice(String restaurantName,MenuItemDto menuItemDto);
}
