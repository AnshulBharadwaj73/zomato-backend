package com.example.foodApp.zomato.zomato.controller;

import com.example.foodApp.zomato.zomato.dto.LoginRequestDto;
import com.example.foodApp.zomato.zomato.dto.LoginResponseDto;
import com.example.foodApp.zomato.zomato.dto.UserDto;
import com.example.foodApp.zomato.zomato.dto.UserRequestDto;
import com.example.foodApp.zomato.zomato.services.AuthUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "User APIs")
public class AuthController {

    private final AuthUserService authUserService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(authUserService.createUser_Delivery(userRequestDto));
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String tokens[] = authUserService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());

        Cookie cookie=new Cookie("token",tokens[0]);
        cookie.setHttpOnly(true);
        cookie.setPath("/");       // Cookie is available to the entire domain
        cookie.setMaxAge(24 * 60 * 60);
        httpServletResponse.addCookie(cookie);


        return ResponseEntity.ok(new LoginResponseDto(tokens[0]));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));

        String accessToken = authUserService.refreshToken(refreshToken);

        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }
}
