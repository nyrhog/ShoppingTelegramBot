package com.nyrhog.telegramShopping.controller;

import com.nyrhog.telegramShopping.entity.*;
import com.nyrhog.telegramShopping.entity.API.InputOrderAPI;
import com.nyrhog.telegramShopping.entity.DTO.FavouriteDTO;
import com.nyrhog.telegramShopping.entity.DTO.ResultDTO;
import com.nyrhog.telegramShopping.entity.DTO.ResultForDTO;
import com.nyrhog.telegramShopping.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClothesRepository clothesRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private OrderClientRepository orderClientRepository;

    @PostMapping("/addToOrder")
    public FavouriteDTO addToOrder(@RequestBody InputOrderAPI data){

        FavouriteDTO favouriteDTO = new FavouriteDTO();
        Client client = clientRepository.findByTelegramUserID(data.getTelegramUserId()).orElse(null);
        Clothes clothes = clothesRepository.findById(data.getClothesId()).orElse(null);
        Size size = sizeRepository.findByName(data.getSize());
        Color color = colorRepository.findByName(data.getColor());

        if(client == null){
            client = new Client();
            client.setTelegramUserID(data.getTelegramUserId());
        }

        OrderClient orderClient = orderClientRepository.findByActiveTrueAndClientTelegramUserID(client.getTelegramUserID());
        if(orderClient == null){
            orderClient = new OrderClient();
            orderClient.addOrderClothes();
        } else {
            orderClient.addOrderClothes();
        }


        favouriteDTO.setMessage("Пользователь был добавлен. Одежда добавлена в \"Заказ\"");
        favouriteDTO.setResult(ResultForDTO.SUCCESS);
        favouriteDTO.setFavouriteAdded(true);

        return favouriteDTO;
    }

    @PostMapping("/deleteFromOrder")
    public FavouriteDTO deleteFromOrder(@RequestBody InputOrderAPI data){

        Client client = clientRepository.findByTelegramUserID(data.getTelegramUserId()).orElse(null);
        Clothes clothes = clothesRepository.findById(data.getClothesId()).orElse(null);


    }
}
