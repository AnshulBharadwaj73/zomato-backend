package com.example.foodApp.zomato.zomato.services.Impl;

import com.example.foodApp.zomato.zomato.dto.AddressDto;
import com.example.foodApp.zomato.zomato.dto.DeliveryBoyDto;
import com.example.foodApp.zomato.zomato.dto.DeliveryBoyRequestDto;
import com.example.foodApp.zomato.zomato.dto.DeliveryRequestDto;
import com.example.foodApp.zomato.zomato.entities.Address;
import com.example.foodApp.zomato.zomato.entities.DeliveryBoy;
import com.example.foodApp.zomato.zomato.entities.User;
import com.example.foodApp.zomato.zomato.repositories.AddressRepository;
import com.example.foodApp.zomato.zomato.repositories.DeliveryBoyRepository;
import com.example.foodApp.zomato.zomato.repositories.UserRepository;
import com.example.foodApp.zomato.zomato.services.DeliveryBoyService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryBoyImpl implements DeliveryBoyService {

    private static final Logger log = LoggerFactory.getLogger(DeliveryBoyImpl.class);
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final DeliveryBoyRepository deliveryBoyRepository;
    private final AddressRepository addressRepository;

    @Override
    public DeliveryBoyDto createDeliveryBoy(DeliveryBoyRequestDto deliveryBoyRequestDto) {
        User user=getCurrentUser();

//        DeliveryBoy deliveryBoy= modelMapper.map(deliveryBoyDto, DeliveryBoy.class);
//        log.info(deliveryBoyDto.getName());
        DeliveryBoy deliveryBoy=new DeliveryBoy();
//        deliveryBoy.setName(deliveryBoyDto.getName());
        deliveryBoy.setVehicleNumber(deliveryBoyRequestDto.getVehicleNumber());
        deliveryBoy.setRating(deliveryBoyRequestDto.getRating());
        deliveryBoy.setIsAvailable(true);
        deliveryBoy.setUser(user);


        AddressDto addressDto = deliveryBoyRequestDto.getAddress();

        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setPostalCode(addressDto.getPostalCode());
        addressRepository.save(address);
        deliveryBoy.setAddress(address);
        Point point = modelMapper.map(deliveryBoyRequestDto.getCurrentLocation(),Point.class);
        deliveryBoy.setCurrentLocation(point);

        DeliveryBoy savedDeliveryBoy = deliveryBoyRepository.save(deliveryBoy);


        return modelMapper.map(savedDeliveryBoy, DeliveryBoyDto.class);
    }

    @Override
    public DeliveryBoyDto endDelivery(DeliveryBoyDto deliveryBoyDto) {
        return null;
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

//    public DeliveryBoy getCurrentUser(){
//        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        return deliveryBoyRepository.findByUser(user).orElseThrow(()-> new RuntimeException("Rider not associated with user with id "+user.getId()));}
}
