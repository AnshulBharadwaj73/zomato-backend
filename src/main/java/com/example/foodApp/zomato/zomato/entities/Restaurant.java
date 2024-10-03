package com.example.foodApp.zomato.zomato.entities;

import com.example.foodApp.zomato.zomato.entities.enums.RestaurantType;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private Double rating;

    private String Otp;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point restaurantLocation;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<MenuItem> menuItems;
}
