package com.nyrhog.telegramShopping.controller;

import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.DTO.ClothesDTO;
import com.nyrhog.telegramShopping.repository.ClothesRepository;
import com.nyrhog.telegramShopping.service.ClothesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClothesController {

    @Autowired
    private ClothesRepository clothesRepository;

    @Autowired
    private ClothesService clothesService;

    @GetMapping("/getOneClothes/{id:\\d+}")
    public @ResponseBody Object getOneClothes(@PathVariable Long id){

        ClothesDTO clothesDTO = clothesService.setClothesDTO(id);

        if(clothesDTO.getId() == null) return "Такой одежды не существует";
        else return clothesDTO;
    }

    @GetMapping("/getAllClothes")
    public List<ClothesDTO> getAllClothes(){

       return clothesService.findAllAndConvertToDTO();
    }

}
