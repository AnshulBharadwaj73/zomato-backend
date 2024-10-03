package com.example.foodApp.zomato.zomato.entities;

import com.example.foodApp.zomato.zomato.entities.enums.OrderStatus;
import com.example.foodApp.zomato.zomato.entities.enums.PaymentMethod;
import com.example.foodApp.zomato.zomato.entities.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_req")
public class OrderRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "orderRequest", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems;

    private Double price;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Double tip;

    private String specialInstruction;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreationTimestamp
    private LocalDateTime orderTime;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
}
