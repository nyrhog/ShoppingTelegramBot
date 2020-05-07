package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Client;
import com.nyrhog.telegramShopping.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAll();

    Client findByTelegramUserID(Long telegID);

    void deleteByTelegramUserID(Long id);


}

