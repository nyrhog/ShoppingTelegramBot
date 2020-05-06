package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Client;
import com.nyrhog.telegramShopping.entity.Order;
import com.nyrhog.telegramShopping.repository.ClientRepository;
import com.nyrhog.telegramShopping.repository.OrderRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
        orderRepository.deleteAll();
        clothesRepository.deleteAll();

        Client client1 = new Client("Sasha", 1L);
        Client client2 = new Client("Pasha", 2L);
        Client client3 = new Client("Tony", 3L);

        clientRepository.saveAll(Arrays.asList(client1, client2, client3));
    }


    @Test
    void deleteOneClient(){
        List<Client> clients = clientRepository.findAll();
        clientRepository.deleteByTelegramUserID(clients.get(0).getTelegramUserID());
        assertNull(clientRepository.findByTelegramUserID(clients.get(0).getTelegramUserID()));
    }

    @Test
    void saveOneClientInDB(){
        Client client = new Client("Kirill", 12451L);
        clientRepository.saveAndFlush(client);

        List<Client> clients = clientRepository.findAll();
        assertEquals(4, clients.size());
    }


    @Test
    void findClientByOrder(){

        Long telegramID = 12322L;

        Order order = new Order(123123d);

        Client client = new Client("Oleg", telegramID);
        client.addOrder(order);

        clientRepository.saveAndFlush(client);

        Client client1 = clientRepository.findByTelegramUserID(telegramID);
        assertEquals(123123d, client1.getOrders().get(0).getTotalPrice());

    }

    @Test
    void deleteClientWithOrder(){

        Order order = new Order(123123d);
        Order order2 = new Order(1222123d);

        Client client = new Client("Anna", 2222L );
        client.addOrder(order);
        client.addOrder(order2);

        clientRepository.saveAndFlush(client);
        clientRepository.deleteByTelegramUserID(client.getTelegramUserID());
        clientRepository.flush();

        assertEquals(0, orderRepository.findAll().size());

        assertNull(clientRepository.findByTelegramUserID(2222L));
    }

}
