package com.nyrhog.telegramShopping.controller;

import com.nyrhog.telegramShopping.entity.API.FavouriteAPI;
import com.nyrhog.telegramShopping.entity.Client;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.DTO.FavouriteDTO;
import com.nyrhog.telegramShopping.entity.DTO.ResultDTO;
import com.nyrhog.telegramShopping.entity.DTO.ResultForDTO;
import com.nyrhog.telegramShopping.entity.FavouriteClothes;
import com.nyrhog.telegramShopping.repository.ClientRepository;
import com.nyrhog.telegramShopping.repository.ClothesRepository;
import com.nyrhog.telegramShopping.repository.FavouriteClothesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FavouriteController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClothesRepository clothesRepository;

    @Autowired
    private FavouriteClothesRepository favouriteClothesRepository;

    @PostMapping("/toggleFavourite")
    public FavouriteDTO toggleFavourite(@RequestBody FavouriteAPI data){

        FavouriteDTO favouriteDTO = new FavouriteDTO();

        Clothes clothes = clothesRepository.findById(data.getClothesId()).orElse(null);
        Client client = clientRepository.findByTelegramUserID(data.getTelegramUserId()).orElse(null);

        if(client == null){
            client = new Client();
            client.setTelegramUserID(data.getTelegramUserId());
            client.addFavouriteClothes(clothes);
            clientRepository.saveAndFlush(client);

            favouriteDTO.setMessage("Пользователь был добавлен. Одежда добавлена в \"Избранное\"");
            favouriteDTO.setResult(ResultForDTO.SUCCESS);
            favouriteDTO.setFavouriteAdded(true);

            return favouriteDTO;
        }

        boolean found = false;

        for (FavouriteClothes favourite: client.getFavouriteClothes()) {
            System.out.println(favourite.getClothes());
            if(favourite.getClothes().equals(clothes)){
                found = true;
                break;
            }
        }

        System.out.println(found);

        if(found){

            favouriteClothesRepository.deleteFavouriteClothes(data.getTelegramUserId(), data.getClothesId());
            clientRepository.saveAndFlush(client);

            favouriteDTO.setMessage("Одежда была удалена из \"Избранное\"");
            favouriteDTO.setResult(ResultForDTO.SUCCESS);
            favouriteDTO.setFavouriteAdded(false);

            return favouriteDTO;
        }

        else{
            client.addFavouriteClothes(clothes);
            clientRepository.saveAndFlush(client);

            favouriteDTO.setMessage("Одежда была добавлена в \"Избранное\"");
            favouriteDTO.setResult(ResultForDTO.SUCCESS);
            favouriteDTO.setFavouriteAdded(true);

            return favouriteDTO;
        }
    }

    @PostMapping("/removeAllClothesFromFavourite")
    public FavouriteDTO removeAllClothesFromFavourite(@RequestBody FavouriteAPI data){
        Client client = clientRepository.findByTelegramUserID(data.getTelegramUserId()).orElse(null);

        favouriteClothesRepository.deleteAllByClient_Id(client.getId());
        clientRepository.saveAndFlush(client);

        FavouriteDTO favouriteDTO = new FavouriteDTO();
        favouriteDTO.setResult(ResultForDTO.SUCCESS);
        favouriteDTO.setMessage("Вся одежда была удалена из \"Избранное\"");

        return favouriteDTO;
    }
}
