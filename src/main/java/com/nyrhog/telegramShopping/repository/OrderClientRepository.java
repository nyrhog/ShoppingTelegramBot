package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.OrderClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderClientRepository extends JpaRepository<OrderClient, Long> {

//    @Query("from OrderClient as oc left join fetch oc.client as cl where cl.telegramUserID =:telegramId " +
//            "and oc.active = true")
    OrderClient findByActiveTrueAndClientTelegramUserID(Long telegramId);
}
