package com.example.foodApp.zomato.zomato.services.Impl;


import com.example.foodApp.zomato.zomato.dto.AddressDto;
import com.example.foodApp.zomato.zomato.dto.RestaurantDto;
import com.example.foodApp.zomato.zomato.dto.RestaurantWithAddressDto;
import com.example.foodApp.zomato.zomato.entities.Address;
import com.example.foodApp.zomato.zomato.entities.Restaurant;
import com.example.foodApp.zomato.zomato.exceptions.RestaurantNotFoundException;
import com.example.foodApp.zomato.zomato.repositories.AddressRepository;
import com.example.foodApp.zomato.zomato.repositories.RestaurantRepository;
import com.example.foodApp.zomato.zomato.services.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;

    @Override
    public RestaurantDto createNewRestaurant(RestaurantDto restaurantDto) {

        Restaurant restaurant= modelMapper.map(restaurantDto, Restaurant.class);
        AddressDto addressDto = restaurantDto.getAddressDto();

        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setPostalCode(addressDto.getPostalCode());
        addressRepository.save(address);

        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(address);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        RestaurantDto responseDto = modelMapper.map(savedRestaurant, RestaurantDto.class);

        AddressDto responseAddressDto = modelMapper.map(savedRestaurant.getAddress(), AddressDto.class);
        responseDto.setAddressDto(responseAddressDto);
        responseDto.setRestaurantLocation(restaurantDto.getRestaurantLocation());

        return responseDto;
    }

    @Override
    public List<Restaurant> findRestaurantByCityName(String city) {
        return restaurantRepository.findByCity(city);
    }

    @Override
    public List<RestaurantWithAddressDto> findRestaurantByRestaurantRating(Double rating) {
        List<Restaurant> restaurants = restaurantRepository.findByRating(rating);
        if(restaurants.isEmpty()){
            throw new RuntimeException("Restaurant not found with rating "+rating);
        }
        return restaurants.stream().map(res -> {
            RestaurantWithAddressDto restDto = new RestaurantWithAddressDto();
            if(res.getRating() >= rating){
                restDto.setRestaurantName(res.getName());
                restDto.setStreet(res.getAddress().getStreet());
                restDto.setState(res.getAddress().getState());
                restDto.setCity(res.getAddress().getCity());
                restDto.setPostalCode(res.getAddress().getPostalCode());
            }
            return restDto;

        }).collect(Collectors.toList());
    }

    @Override
    public List<RestaurantWithAddressDto> findRestaurantByRestaurantName(String restaurantName) {
        List<Restaurant> restaurants = restaurantRepository.findByName(restaurantName).stream().toList();
        if(restaurants.isEmpty()){
            throw new RestaurantNotFoundException("Restaurant not found with rating "+restaurantName);
        }
        return restaurants.stream().map(res -> {
            RestaurantWithAddressDto restDto = new RestaurantWithAddressDto();
            restDto.setRestaurantName(res.getName());
            restDto.setStreet(res.getAddress().getStreet());
            restDto.setState(res.getAddress().getState());
            restDto.setCity(res.getAddress().getCity());
            restDto.setPostalCode(res.getAddress().getPostalCode());
            return restDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteRestaurantWithRestaurantId(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()-> new RestaurantNotFoundException("No restaurantfound with id "+restaurantId));
        restaurantRepository.delete(restaurant);
    }
}
