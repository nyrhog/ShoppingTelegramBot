package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.FavouriteClothes;
import com.nyrhog.telegramShopping.entity.FavouriteClothesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FavouriteClothesRepository extends JpaRepository<FavouriteClothes, FavouriteClothesId> {

    @Transactional
    @Modifying
    @Query("DELETE FROM FavouriteClothes fc WHERE fc.client.id = :clientId and fc.clothes.id = :clothesId")
    void deleteFavouriteClothes(Long clientId, Long clothesId);

    @Transactional
    @Modifying
    @Query("DELETE FROM FavouriteClothes fc WHERE fc.client.id = :id")
    void deleteAllByClient_Id(Long id);
}
