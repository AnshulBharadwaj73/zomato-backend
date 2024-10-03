package com.example.foodApp.zomato.zomato.entities;

import com.example.foodApp.zomato.zomato.entities.enums.DeliveryStatus;
import com.example.foodApp.zomato.zomato.entities.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_request_id")
    private OrderRequest orderRequest;

    @ManyToOne
    @JoinColumn(name = "delivery_boy_id")
    private DeliveryBoy deliveryBoy;

    private LocalDateTime pickupTime;

    private String Otp;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point pickupLocation;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point dropOffLocation;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
