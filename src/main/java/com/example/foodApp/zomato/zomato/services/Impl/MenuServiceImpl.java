package com.example.foodApp.zomato.zomato.services.Impl;

import com.example.foodApp.zomato.zomato.dto.MenuItemDto;
import com.example.foodApp.zomato.zomato.entities.MenuItem;
import com.example.foodApp.zomato.zomato.entities.Restaurant;
import com.example.foodApp.zomato.zomato.exceptions.RestaurantNotFoundException;
import com.example.foodApp.zomato.zomato.repositories.MenuItemRepository;
import com.example.foodApp.zomato.zomato.repositories.RestaurantRepository;
import com.example.foodApp.zomato.zomato.services.MenuService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;
    private final ModelMapper modelMapper;
    private final RestaurantRepository restaurantRepository;


    @Override
    public MenuItemDto createNewMenu(MenuItemDto menuItemDto) {
        MenuItem menuItem = modelMapper.map(menuItemDto, MenuItem.class);

        Restaurant restaurant = restaurantRepository.findById(menuItemDto.getRestaurantId()).orElseThrow(()-> new RestaurantNotFoundException("Restaurant with id not found "+menuItemDto.getRestaurantId()));
//        menuItem.setName(menuItemDto.getName());
        menuItem.setId(null);
//        menuItem.setDescription(menuItemDto.getDescription());
//        menuItem.setPrice(menuItemDto.getPrice());
//        menuItem.setRating(menuItemDto.getRating());
//        menuItem.setIsAvailable(menuItemDto.getIsAvailable());
        menuItem.setRestaurant(restaurant);

        menuItemRepository.save(menuItem);
        return modelMapper.map(menuItem, MenuItemDto.class);
    }

    @Override
    public MenuItemDto updateExistingMenuPrice(String restaurantName,MenuItemDto menuItemDto) {

        MenuItem menuItem = menuItemRepository.findByName(restaurantName).orElseThrow(()-> new RuntimeException("Restaurant with name not found "+restaurantName));
        if(menuItem!=null){
            Restaurant restaurant = restaurantRepository.findById(menuItemDto.getRestaurantId()).orElseThrow(()-> new RestaurantNotFoundException("Restaurant with id not found "+menuItemDto.getRestaurantId()));
//        menuItem.setName(menuItemDto.getName());
            menuItem.setId(null);
//        menuItem.setDescription(menuItemDto.getDescription());
//        menuItem.setPrice(menuItemDto.getPrice());
//        menuItem.setRating(menuItemDto.getRating());
//        menuItem.setIsAvailable(menuItemDto.getIsAvailable());
            menuItem.setRestaurant(restaurant);

            menuItemRepository.save(menuItem);

        }
        return modelMapper.map(menuItem, MenuItemDto.class);
    }
}
