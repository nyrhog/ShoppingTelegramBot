package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Category;
import com.nyrhog.telegramShopping.entity.Client;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderRepositoryTest {

    @Autowired
    private  OrderRepository orderRepository;

    @Autowired
    private  ClothesRepository clothesRepository;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setup(){
        orderRepository.deleteAll();
        clothesRepository.deleteAll();
    }

    @Test
    void saveOneOrder(){
        Order order = new Order(50000d);

        orderRepository.saveAndFlush(order);

       List<Order> orders = orderRepository.findAll();
       assertEquals(1, orders.size());

    }

    @Test
    void updateValueOfOrder(){
        Order order = new Order(50000d);

        orderRepository.saveAndFlush(order);
        List<Order> orders = orderRepository.findAll();
        orders.get(0).setTotalPrice(12000d);
        orderRepository.saveAndFlush(orders.get(0));
        orders = orderRepository.findAll();

        assertEquals(12000d, orders.get(0).getTotalPrice());

    }

    @Test
    void deleteOrder(){
        Order order = new Order();
        order.setTotalPrice(50000d);

        orderRepository.saveAndFlush(order);
        orderRepository.findByTotalPrice(50000d);
        orderRepository.deleteById(order.getId());

        assertEquals(0, orderRepository.findAll().size());
    }


    @Test
    void deleteOrderWithoutClothes(){
        Clothes armaniBoots = new Clothes();
        armaniBoots.setName("EA7 Emporio Armani");
        clothesRepository.save(armaniBoots);

        Clothes armaniJacket = new Clothes();
        armaniBoots.setName("Платье-футляр из шелкового кади");
        clothesRepository.save(armaniJacket);

        Order order = new Order();
        order.setTotalPrice(500000d);
        order.addClothes(clothesRepository.findById(1L).orElse(null));
        order.addClothes(clothesRepository.findById(2L).orElse(null));

        orderRepository.saveAndFlush(order);

        order = orderRepository.findByTotalPrice(500000d);

        orderRepository.deleteById(order.getId());

        assertEquals(0, orderRepository.findAll().size());
        assertEquals(2, clothesRepository.findAll().size());
    }
}