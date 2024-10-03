package com.example.foodApp.zomato.zomato.services.Impl;

import com.example.foodApp.zomato.zomato.dto.UserDto;
import com.example.foodApp.zomato.zomato.dto.UserRequestDto;
import com.example.foodApp.zomato.zomato.entities.User;
import com.example.foodApp.zomato.zomato.entities.enums.Role;
import com.example.foodApp.zomato.zomato.repositories.UserRepository;
import com.example.foodApp.zomato.zomato.security.JWTService;
import com.example.foodApp.zomato.zomato.services.AuthUserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser_Delivery(UserRequestDto userRequestDto) {

        User userEmail =userRepository.findByEmail(userRequestDto.getEmail()).orElse(null);
        if(userEmail!=null){
            throw new RuntimeException("cannot signup ,User already exists with email "+userRequestDto.getEmail());
        }

        User user = modelMapper.map(userRequestDto, User.class);
//        user.setPassword(userRequestDto.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = userRequestDto.getRoles().stream()
                .map(role -> Role.valueOf(role.name())) // Example transformation, optional
                .collect(Collectors.toSet());

        user.setRoles(roles);
        User user1=userRepository.save(user);

        return modelMapper.map(user1, UserDto.class);
    }

    @Override
    public String[] login(String email, String password) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        User user=(User)authentication.getPrincipal();

        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken,refreshToken};
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found " +
                "with id: "+userId));

        return jwtService.generateAccessToken(user);
    }
}
