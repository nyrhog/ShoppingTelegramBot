package com.nyrhog.telegramShopping.controller;

import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.repository.ClothesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClothesController {

    @Autowired
    private ClothesRepository clothesRepository;

    @GetMapping("/getOneClothes/{id:\\d+}")
    public @ResponseBody Object getOneClothes(@PathVariable Long id){

        Clothes clothes = clothesRepository.findById(id).orElse(null);

        if(clothes!=null) return clothes;
        else return "Такой одежды не существует";
    }

}
