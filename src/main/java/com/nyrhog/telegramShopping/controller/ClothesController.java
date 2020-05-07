package com.nyrhog.telegramShopping.controller;

import com.nyrhog.telegramShopping.entity.API.FilterAPI;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.DTO.ClothesDTO;
import com.nyrhog.telegramShopping.repository.ClothesRepository;
import com.nyrhog.telegramShopping.service.ClothesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClothesController {

    @Autowired
    private ClothesRepository clothesRepository;

    @Autowired
    private ClothesService clothesService;

    @GetMapping("/getOneClothes/{id:\\d+}")
    public Object getOneClothes(@PathVariable Long id){

        ClothesDTO clothesDTO = clothesService.setClothesDTO(id);

        if(clothesDTO.getId() == null) return "Такой одежды не существует";
        else return clothesDTO;
    }

    @GetMapping("/getAllClothes")
    public List<ClothesDTO> getAllClothes(){

       return clothesService.findAllClothesAndConvertToDTO();

    }

    @PostMapping("/test")
    public String postTest(@RequestBody FilterAPI data){
        System.out.println("это дата: " + data);
        return "work";
    }


}
