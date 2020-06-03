package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.FavouriteClothes;
import com.nyrhog.telegramShopping.entity.FavouriteClothesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteClothesRepository extends JpaRepository<FavouriteClothes, FavouriteClothesId> {

    void deleteByClient_IdAndClothes_Id(Long clientId, Long clothesId);
}
