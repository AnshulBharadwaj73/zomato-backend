package com.example.foodApp.zomato.zomato.services;

import com.example.foodApp.zomato.zomato.dto.UserDto;
import com.example.foodApp.zomato.zomato.dto.UserRequestDto;

public interface AuthUserService {

    UserDto createUser_Delivery(UserRequestDto userRequestDtoDto);
    String[] login(String email,String password);
    String refreshToken(java.lang.String refreshToken);

}
