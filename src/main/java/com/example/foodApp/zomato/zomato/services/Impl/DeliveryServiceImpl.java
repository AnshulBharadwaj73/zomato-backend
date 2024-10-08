package com.example.foodApp.zomato.zomato.services.Impl;

import com.example.foodApp.zomato.zomato.dto.DeliveryRequestDto;
import com.example.foodApp.zomato.zomato.dto.DeliveryResponseDto;
import com.example.foodApp.zomato.zomato.dto.OtpDto;
import com.example.foodApp.zomato.zomato.entities.*;
import com.example.foodApp.zomato.zomato.entities.enums.DeliveryStatus;
import com.example.foodApp.zomato.zomato.entities.enums.OrderStatus;
import com.example.foodApp.zomato.zomato.entities.enums.PaymentMethod;
import com.example.foodApp.zomato.zomato.exceptions.OtpMisMatchedException;
import com.example.foodApp.zomato.zomato.repositories.*;
import com.example.foodApp.zomato.zomato.services.DeliveryService;
import com.example.foodApp.zomato.zomato.services.PaymentService;
import com.example.foodApp.zomato.zomato.strategies.PaymentStrategy;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRequestRepository deliveryRequestRepository;
    private final OrderRequestRepository orderRequestRepository;
    private final DeliveryBoyRepository deliveryBoyRepository;
    private final RestaurantRepository restaurantRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentStrategy paymentStrategy;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;

    @Override
    public DeliveryResponseDto acceptDeliveryByDeliveryBoy(Long orderRequestId, DeliveryRequestDto deliveryRequestDto) {
        DeliveryBoy deliveryBoy = getCurrentDeliveryBoy();
        if(!deliveryBoy.getIsAvailable()){
            throw new RuntimeException("delivery Boy with id not found");
        }
        OrderRequest orderRequest = orderRequestRepository.findById(orderRequestId).orElseThrow(()-> new RuntimeException("Order Request with id not found"));

        if(orderRequest.getOrderStatus()!=OrderStatus.CONFIRMED){
            throw new RuntimeException("Cannot create DeliveryRequest as the status is not CONFIRMED");
        }

        Payment payment = paymentRepository.findById(orderRequest.getId()).orElseThrow(()-> new RuntimeException("payment not found"));
        paymentService.createPayment(orderRequest);

        if(orderRequest.getPaymentMethod()!= PaymentMethod.COD && !payment.getIsPaid()){
            throw new RuntimeException("Order Cannot accept as it is not paid");
        }


        orderRequest.setOrderStatus(OrderStatus.PICKED_UP);
        orderRequest.setUpdatedAt(LocalDateTime.now());
        orderRequestRepository.save(orderRequest);

        DeliveryRequest deliveryRequest = modelMapper.map(deliveryRequestDto, DeliveryRequest.class);
        deliveryRequest.setDeliveryBoy(deliveryBoy);
        deliveryRequest.setDeliveryStatus(DeliveryStatus.PICKING_UP);
        deliveryRequest.setOrderRequest(orderRequest);
        deliveryRequest.setPickupTime(LocalDateTime.now());

        deliveryBoy.setIsAvailable(false);
        deliveryBoyRepository.save(deliveryBoy);

        String otp = generateRandomOTP();
        deliveryRequest.setOtp(otp);

        Restaurant restaurant=deliveryRequest.getOrderRequest().getRestaurant();
        restaurant.setOtp(otp);
        restaurantRepository.save(restaurant);

        DeliveryRequest savedRequest = deliveryRequestRepository.save(deliveryRequest);

        return modelMapper.map(savedRequest, DeliveryResponseDto.class);

    }

    @Override
    public DeliveryRequestDto endDeliveryByDeliveryBoy(Long deliveryId, OtpDto otpDto) {
        DeliveryRequest deliveryRequest = deliveryRequestRepository.findByOrderRequestId(deliveryId).orElseThrow(()-> new RuntimeException("No Delivery Request Found with id "+deliveryId));
        if(!Objects.equals(deliveryRequest.getOtp(), otpDto.getOtp())){
            throw new OtpMisMatchedException("Otp Doesn't matched hence cannot be deliver");
        }
        deliveryRequest.setDeliveryStatus(DeliveryStatus.DELIVERED);

        OrderRequest orderRequest = orderRequestRepository.findById(deliveryRequest.getOrderRequest().getId()).orElseThrow(()->new RuntimeException("order request id not found"));
        if(orderRequest.getPaymentMethod() == PaymentMethod.COD){
            Payment payment = paymentRepository.findByOrderRequest(orderRequest).orElseThrow(()-> new RuntimeException("Order Request not found "));
            paymentStrategy.processPayment(payment);
        }
        orderRequest.setOrderStatus(OrderStatus.DELIVERED);
        orderRequestRepository.save(orderRequest);

//         deliveryBoy = available
        deliveryRequest.getDeliveryBoy().setIsAvailable(true);
        DeliveryRequest saveddeliveryRequest = deliveryRequestRepository.save(deliveryRequest);


        return modelMapper.map(saveddeliveryRequest, DeliveryRequestDto.class);

    }


    private String generateRandomOTP() {
        Random random = new Random();
        int otpInt = random.nextInt(10000);  //0 to 9999
        return String.format("%04d", otpInt);
    }

//    @Override
//    public DeliveryBoy getCurrentDeliveryBoy() {
//        return deliveryBoyRepository.findById(1L).orElseThrow(() -> new RuntimeException("Driver not found with " +
//                "id "+1));
//    }

    public DeliveryBoy getCurrentDeliveryBoy(){
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        return deliveryBoyRepository.findByUser(user).orElseThrow(()-> new RuntimeException("DeliveryBoy not associated with user with id "+user.getId()));
    }
}
