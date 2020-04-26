package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Category;
import com.nyrhog.telegramShopping.entity.Client;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private  OrderRepository orderRepository;

    @Autowired
    private  ClothesRepository clothesRepository;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void sutUp(){
        orderRepository.deleteAll();
        clothesRepository.deleteAll();
    }

    @Test
    void saveOneOrder(){
        Order order = new Order();
        order.setTotalPrice(50000d);

        orderRepository.save(order);

       List<Order> orders = orderRepository.findAll();
       assertEquals(1, orders.size());

    }

    @Test
    void updateValueOfOrder(){
        Order order = new Order();
        order.setTotalPrice(50000d);

        orderRepository.save(order);
        List<Order> orders = orderRepository.findAll();
        orders.get(0).setTotalPrice(12000d);
        orderRepository.save(orders.get(0));
        orders = orderRepository.findAll();

        assertEquals(12000d, orders.get(0).getTotalPrice());

    }

    @Test
    void deleteOrder(){
        Order order = new Order();
        order.setTotalPrice(50000d);

        orderRepository.save(order);

        orderRepository.findByTotalPrice(50000d);

        orderRepository.deleteById(order.getId());

        assertEquals(0, orderRepository.findAll().size());
    }

    @Transactional
    @Test
    void deleteOrderWithoutClothes(){
        Clothes armaniBoots = new Clothes();
        armaniBoots.setCategory(Category.BOOTS);
        armaniBoots.setName("EA7 Emporio Armani");
        clothesRepository.save(armaniBoots);

        Clothes armaniJacket = new Clothes();
        armaniBoots.setCategory(Category.JACKET);
        armaniBoots.setName("Платье-футляр из шелкового кади");
        clothesRepository.save(armaniJacket);

        Order order = new Order();
        order.setTotalPrice(500000d);
        order.addClothes(clothesRepository.findById(1L).orElse(null));
        order.addClothes(clothesRepository.findById(2L).orElse(null));

        orderRepository.save(order);

        order = orderRepository.findByTotalPrice(500000d);

        orderRepository.deleteById(order.getId());

        assertEquals(0, orderRepository.findAll().size());
        assertEquals(2, clothesRepository.findAll().size());
    }
}