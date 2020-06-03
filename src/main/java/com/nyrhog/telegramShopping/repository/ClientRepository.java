package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAll();

    @Query("from Client client left join fetch client.favouriteClothes " +
            "fc left join fetch fc.clothes where client.telegramUserID = :id")
    Optional<Client> findByTelegramUserID(Long id);

    void deleteByTelegramUserID(Long id);

}

