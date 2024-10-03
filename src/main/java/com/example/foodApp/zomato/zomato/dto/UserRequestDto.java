package com.example.foodApp.zomato.zomato.dto;

import com.example.foodApp.zomato.zomato.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private Set<Role> roles;
}
