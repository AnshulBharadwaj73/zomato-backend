package com.example.foodApp.zomato.zomato.controller;

import com.example.foodApp.zomato.zomato.advices.ApiResponse;
import com.example.foodApp.zomato.zomato.dto.CartDto;
import com.example.foodApp.zomato.zomato.dto.MenuItemDto;
import com.example.foodApp.zomato.zomato.dto.RestaurantDto;
import com.example.foodApp.zomato.zomato.dto.RestaurantWithAddressDto;
import com.example.foodApp.zomato.zomato.entities.Restaurant;
import com.example.foodApp.zomato.zomato.repositories.RestaurantRepository;
import com.example.foodApp.zomato.zomato.services.CartService;
import com.example.foodApp.zomato.zomato.services.MenuService;
import com.example.foodApp.zomato.zomato.services.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@Tag(name = "Restaurant APIs")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final CartService cartService;
    private final RestaurantRepository restaurantRepository;

    @PostMapping("/createNewRestaurant")
    public ResponseEntity<RestaurantDto> createNewRestaurant(@RequestBody RestaurantDto restaurantDto){
//        System.out.println(restaurantDto);
        return ResponseEntity.ok(restaurantService.createNewRestaurant(restaurantDto));
    }

    @PostMapping("/createMenu")
    public ResponseEntity<MenuItemDto> createMenu(@RequestBody MenuItemDto menuItemDto){
        return ResponseEntity.ok(menuService.createNewMenu(menuItemDto));
    }

    @PostMapping("/createCart")
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto){
        return ResponseEntity.ok(cartService.createCart(cartDto));
    }

    @GetMapping("/city/{cityName}")
    public ResponseEntity<List<RestaurantWithAddressDto>> getRestaurantByName(@PathVariable String cityName){
        List<Restaurant> restaurants = restaurantRepository.findByCity(cityName);
        if(restaurants.isEmpty()){
            throw new RuntimeException("Restaurant not found with city name "+cityName);
        }
        return ResponseEntity.ok(restaurants.stream().map(res -> {
            RestaurantWithAddressDto restDto = new RestaurantWithAddressDto();
            restDto.setRestaurantName(res.getName());
            restDto.setStreet(res.getAddress().getStreet());
            restDto.setState(res.getAddress().getState());
            restDto.setCity(res.getAddress().getCity());
            restDto.setPostalCode(res.getAddress().getPostalCode());
            return restDto;
        }).collect(Collectors.toList()));
    }

    @GetMapping("rating/{rating}")
    public ResponseEntity<List<RestaurantWithAddressDto>> getRestaurantWithRating(@PathVariable Double rating){
        return ResponseEntity.ok(restaurantService.findRestaurantByRestaurantRating(rating));
    }

    @GetMapping("restaurantName/{name}")
    public ResponseEntity<List<RestaurantWithAddressDto>> getRestaurantWithName(@PathVariable String name){
        return ResponseEntity.ok(restaurantService.findRestaurantByRestaurantName(name));
    }

    @Secured("Role_Admin")
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<ApiResponse> deleteRestaurant(@PathVariable Long restaurantId){
        restaurantService.deleteRestaurantWithRestaurantId(restaurantId);
        ApiResponse res = new ApiResponse<>();
        return ResponseEntity.ok(res);
    }
}
