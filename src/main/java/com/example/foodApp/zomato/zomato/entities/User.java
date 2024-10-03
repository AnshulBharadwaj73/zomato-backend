package com.example.foodApp.zomato.zomato.entities;

import com.example.foodApp.zomato.zomato.entities.enums.Role;
import com.example.foodApp.zomato.zomato.services.UserService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "app_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
        return roles.stream().map(role -> new SimpleGrantedAuthority("Role_"+role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return "";
    }
}
