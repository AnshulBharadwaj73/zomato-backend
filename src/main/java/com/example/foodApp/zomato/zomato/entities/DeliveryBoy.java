package com.example.foodApp.zomato.zomato.entities;

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
public class DeliveryBoy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @Column(name = "vehicle_id", columnDefinition = "VARCHAR(255)")
    private String vehicleNumber;

    private Boolean isAvailable;

    private Double rating;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point currentLocation;

//    @OneToMany(mappedBy = "deliveryBoy")
//    private List<DeliveryRequest> deliveryRequests;
}