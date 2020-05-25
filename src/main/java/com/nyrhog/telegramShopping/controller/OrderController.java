package com.nyrhog.telegramShopping.controller;

import com.nyrhog.telegramShopping.entity.API.InputOrderAPI;
import com.nyrhog.telegramShopping.entity.Client;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.DTO.ResultDTO;
import com.nyrhog.telegramShopping.entity.DTO.ResultForDTO;
import com.nyrhog.telegramShopping.entity.Order;
import com.nyrhog.telegramShopping.repository.ClientRepository;
import com.nyrhog.telegramShopping.repository.ClothesRepository;
import com.nyrhog.telegramShopping.repository.OrderRepository;
import com.nyrhog.telegramShopping.service.ClothesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    ClothesService clothesService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ClothesRepository clothesRepository;

    @Autowired
    ClientRepository clientRepository;

//    @PostMapping("/addOrder")
//    public String addOrder(@RequestBody InputOrderAPI data){
//
//        Order order = new Order();
//        order.addClothes(clothesRepository.findById(data.getClothesId()).orElse(null));
//
//        orderRepository.save(order);
//        orderRepository.flush();
//
//        return "TODO";
//
//    }

}
