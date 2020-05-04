package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Client;
import com.nyrhog.telegramShopping.entity.Order;
import com.nyrhog.telegramShopping.repository.ClientRepository;
import com.nyrhog.telegramShopping.repository.OrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


@SpringBootTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClothesRepository clothesRepository;

    @BeforeEach
    void setup(){
        clientRepository.deleteAll();

        Client client1 = new Client();
        client1.setName("Sasha");
        client1.setTelegramUserID(1L);

        Client client2 = new Client();
        client2.setName("Pasha");
        client2.setTelegramUserID(2L);

        Client client3 = new Client();
        client3.setName("Tony");
        client3.setTelegramUserID(3L);

        clientRepository.save(client1);
        clientRepository.save(client2);
        clientRepository.save(client3);
    }



    @Test
    void deleteOneClient(){
        List<Client> clients = clientRepository.findAll();
        clientRepository.delete(clients.get(0));
        clients = clientRepository.findAll();
        assertEquals(2, clients.size());
    }

    @Test
    void saveOneClientInDB(){
        Client client = new Client();
        client.setName("Kirill");
        clientRepository.save(client);

        List<Client> clients = clientRepository.findAll();
        assertEquals(4, clients.size());
    }

    @Transactional
    @Test
    void findClientByOrder(){

        Long telegramID = 12322L;

        Order order = new Order();
        order.setTotalPrice(123123d);

        Client client = new Client();
        client.setTelegramUserID(telegramID);
        client.setName("Oleg");
        client.addOrder(order);

        clientRepository.save(client);

        Client client1 = clientRepository.findByTelegramUserID(telegramID);
        System.out.println(client1.getOrders().get(0).getTotalPrice());
        System.out.println(client1);
    }

    @Test
    void deleteClientWithOrder(){

        Order order = new Order();
        order.setTotalPrice(123123d);

        Order order2 = new Order();
        order.setTotalPrice(1222123d);

        Client client = new Client();
        client.setTelegramUserID(2222L);
        client.setName("Anna");
        client.addOrder(order);
        client.addOrder(order2);

        clientRepository.save(client);
        clientRepository.delete(client);

        assertEquals(0, orderRepository.findAll().size());

        assertNull(clientRepository.findByTelegramUserID(2222L));
    }

}
