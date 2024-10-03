package com.example.foodApp.zomato.zomato.services.Impl;

import com.example.foodApp.zomato.zomato.dto.CartDto;
import com.example.foodApp.zomato.zomato.dto.CartItemDto;
import com.example.foodApp.zomato.zomato.dto.MenuItemDto;
import com.example.foodApp.zomato.zomato.dto.UserDto;
import com.example.foodApp.zomato.zomato.entities.*;
import com.example.foodApp.zomato.zomato.repositories.CartRepository;
import com.example.foodApp.zomato.zomato.repositories.MenuItemRepository;
import com.example.foodApp.zomato.zomato.repositories.RestaurantRepository;
import com.example.foodApp.zomato.zomato.repositories.UserRepository;
import com.example.foodApp.zomato.zomato.services.CartService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public CartDto createCart(CartDto cartDto) {
        Cart cart = modelMapper.map(cartDto, Cart.class);

        // Set the user and updated time
        cart.setUser(getCurrentUser());
        cart.setUpdatedAt(LocalDateTime.now());

        // Map CartItems and associate with MenuItem and Restaurant
        List<CartItem> cartItems = cartDto.getItems().stream()
                .map(cartItemDto -> {
                    CartItem cartItem = new CartItem();
                    cartItem.setQuantity(cartItemDto.getQuantity());

                    // Fetch the Restaurant entity using restaurantId from MenuItemDto
                    Long restaurantId = cartItemDto.getMenuItemDto().getRestaurantId();
                    Restaurant restaurant = restaurantRepository.findById(restaurantId)
                            .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));

                    MenuItemDto menuItemDto = cartItemDto.getMenuItemDto();
                    MenuItem menuItem = menuItemRepository.findByNameAndRestaurant(menuItemDto.getName(), restaurant)
                            .orElseGet(() -> {
                                // If MenuItem doesn't exist, create and save a new one
                                MenuItem newMenuItem = new MenuItem();
                                newMenuItem.setName(menuItemDto.getName());
                                newMenuItem.setPrice(menuItemDto.getPrice());
                                newMenuItem.setDescription(menuItemDto.getDescription());
                                newMenuItem.setIsAvailable(menuItemDto.getIsAvailable());
                                newMenuItem.setRating(menuItemDto.getRating());
                                newMenuItem.setRestaurant(restaurant);

                                // Save the new MenuItem before assigning it to CartItem
                                return menuItemRepository.save(newMenuItem);
                            });

                    // Set the MenuItem to the CartItem
                    cartItem.setMenuItem(menuItem);

                    cartItem.setCart(cart);

                    return cartItem;
                })
                .collect(Collectors.toList());

        // Set the list of CartItems in the cart entity
        cart.setItems(cartItems);

        // Calculate total price
        Double totalPrice = cartItems.stream()
                .map(cartItem -> cartItem.getMenuItem().getPrice() * cartItem.getQuantity())
                .reduce(0.0, Double::sum);
        cart.setTotalPrice(totalPrice);

        // Save cart and map it back to CartDto
        Cart savedCart = cartRepository.save(cart);
        CartDto responseCartDto = modelMapper.map(savedCart, CartDto.class);
        responseCartDto.setUserDto(modelMapper.map(savedCart.getUser(), UserDto.class));  // Map User to UserDto

        // Ensure items are mapped correctly
        List<CartItemDto> responseCartItems = savedCart.getItems().stream()
                .map(cartItem -> {
                    CartItemDto cartItemDto = modelMapper.map(cartItem, CartItemDto.class);
                    cartItemDto.setMenuItemDto(modelMapper.map(cartItem.getMenuItem(), MenuItemDto.class));  // Map MenuItem
                    return cartItemDto;
                })
                .collect(Collectors.toList());

        responseCartDto.setItems(responseCartItems);
        responseCartDto.setTotalPrice(totalPrice);  // Set total price

        return responseCartDto;
    }

    public User getCurrentUser(){
        return userRepository.findById(2L).orElseThrow(()-> new RuntimeException("User Not exist with id 1"));
    }
}
