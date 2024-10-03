package com.example.foodApp.zomato.zomato.services.Impl;

import com.example.foodApp.zomato.zomato.dto.AddressDto;
import com.example.foodApp.zomato.zomato.dto.OrderRequestDto;
import com.example.foodApp.zomato.zomato.dto.RestaurantDto;
import com.example.foodApp.zomato.zomato.dto.UserDto;
import com.example.foodApp.zomato.zomato.entities.*;
import com.example.foodApp.zomato.zomato.entities.enums.OrderStatus;
import com.example.foodApp.zomato.zomato.entities.enums.PaymentMethod;
import com.example.foodApp.zomato.zomato.entities.enums.PaymentStatus;
import com.example.foodApp.zomato.zomato.exceptions.MenuItemNotFoundException;
import com.example.foodApp.zomato.zomato.exceptions.OrderNotFoundException;
import com.example.foodApp.zomato.zomato.exceptions.RestaurantNotFoundException;
import com.example.foodApp.zomato.zomato.repositories.*;
import com.example.foodApp.zomato.zomato.services.OrderRequestService;
import com.example.foodApp.zomato.zomato.services.PaymentService;
import com.example.foodApp.zomato.zomato.strategies.PaymentStrategy;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderRequestServiceImpl implements OrderRequestService {

    private final OrderRequestRepository orderRequestRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentService paymentService;
    private final PaymentStrategy paymentStrategy;
    private final ModelMapper modelMapper;
    private final PaymentRepository paymentRepository;

    @Override
    public OrderRequestDto createNewOrderRequest(String restaurantName, OrderRequestDto orderRequestDto) {
        User user = getCurrentUser();

        // Step 1: Map and create the OrderRequest entity first
        OrderRequest orderRequest = modelMapper.map(orderRequestDto, OrderRequest.class);
        orderRequest.setOrderStatus(OrderStatus.CREATED);
        orderRequest.setUser(user);
        orderRequest.setUpdatedAt(LocalDateTime.now());
        orderRequest.setPaymentMethod(PaymentMethod.UPI);

        // Find the restaurant by name
        Restaurant restaurant = restaurantRepository.findByName(restaurantName)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with name " + restaurantName + " not found"));

        orderRequest.setRestaurant(restaurant);

        // Create and save OrderItems
        List<OrderItem> orderItems = orderRequestDto.getOrderItems().stream()
                .map(orderItemDto -> {
                    MenuItem menuItem = menuItemRepository.findById(orderItemDto.getMenuItemId())
                            .orElseThrow(() -> new MenuItemNotFoundException("Menu Item Not found"));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(orderItemDto.getQuantity());
                    orderItem.setMenuItem(menuItem);
                    orderItem.setOrderRequest(orderRequest);
                    orderItem.setOrderStatus(OrderStatus.CREATED);
                    orderItem.setUpdatedAt(LocalDateTime.now());
                    orderItem.setPrice(menuItem.getPrice() * orderItemDto.getQuantity());

                    return orderItem;
                })
                .collect(Collectors.toList());

//        orderItemRepository.saveAll(orderItems);
        orderRequest.setOrderItems(orderItems);

        // Calculate total price
        Double totalPrice = orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(0.0, Double::sum);
        orderRequest.setPrice(totalPrice);
        orderRequest.setPaymentStatus(PaymentStatus.PENDING);
        OrderRequest savedRequest = orderRequestRepository.save(orderRequest);
        OrderRequestDto responseDto = modelMapper.map(savedRequest, OrderRequestDto.class);
        RestaurantDto restaurantDto = modelMapper.map(restaurant, RestaurantDto.class);
        responseDto.setRestaurantDto(restaurantDto);
        responseDto.setUserDto(modelMapper.map(user, UserDto.class));

        paymentService.createPayment(orderRequest);

        // Save OrderRequest

        return responseDto;
    }



    @Override
    public OrderRequestDto confirmExistingOrder(Long id) {
        OrderRequest orderRequest=orderRequestRepository.getOrderById(id).orElseThrow(()->new OrderNotFoundException("Order with id "+id+" doesnot exist"));

        orderRequest.setOrderStatus(OrderStatus.CONFIRMED);
        orderRequest.setPaymentStatus(PaymentStatus.PENDING);
//        paymentService.createPayment(orderRequest);
        orderRequest.setUpdatedAt(LocalDateTime.now());
        Payment payment=paymentRepository.findByOrderRequest(orderRequest).orElseThrow(()-> new OrderNotFoundException("Order Not Found"));
        if(orderRequest.getPaymentMethod() == PaymentMethod.UPI){
            paymentStrategy.processPayment(payment);

        }else if(orderRequest.getPaymentMethod() == PaymentMethod.COD){
            paymentStrategy.processPayment(payment);
        }
        else{
            throw new RuntimeException("Cannot process the payment for UPI method");
        }
;        paymentRepository.save(payment);
        orderRequestRepository.save(orderRequest);

        return modelMapper.map(orderRequest, OrderRequestDto.class);
    }

    @Override
    public void deleteOrderByOrderId(Long orderId) {
        OrderRequest orderRequest = orderRequestRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("Order not found with orderId: "+orderId));
        orderRequestRepository.delete(orderRequest);
    }

    @Override
    public OrderRequestDto confirmPaymentForOrderRequest(Long orderRequestId) {
        OrderRequest orderRequest =orderRequestRepository.findById(orderRequestId).orElseThrow(()-> new OrderNotFoundException("order request with id not found "+orderRequestId));
        paymentService.updatePayment(orderRequestId);
        return modelMapper.map(orderRequest, OrderRequestDto.class);
    }

    @Override
    public void deleteOrderWithOrderId(Long orderId) {
        OrderRequest orderRequest = orderRequestRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("No order found with "+orderId));
        orderRequestRepository.delete(orderRequest);
    }


    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
