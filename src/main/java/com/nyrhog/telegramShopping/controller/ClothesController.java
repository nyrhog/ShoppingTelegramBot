package com.nyrhog.telegramShopping.controller;

import com.nyrhog.telegramShopping.entity.API.FilterAPI;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.DTO.ClothesDTO;
import com.nyrhog.telegramShopping.entity.DTO.ResultDTO;
import com.nyrhog.telegramShopping.repository.ClothesRepository;
import com.nyrhog.telegramShopping.service.ClothesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

@RestController
public class ClothesController {

    @Autowired
    private ClothesRepository clothesRepository;

    @Autowired
    private ClothesService clothesService;

    @GetMapping("/getOneClothes/{id:\\d+}")
    public ResultDTO getOneClothes(@PathVariable Long id){

        ResultDTO resultDTO = new ResultDTO();
        ClothesDTO clothesDTO = clothesService.setClothesDTO(id);

        if(clothesDTO.getId() == null) {
            resultDTO.setMessage("Такой одежды не существует");
            return resultDTO;
        }

        resultDTO.getClothesDTOList().add(clothesDTO);

        return resultDTO;
    }

    @GetMapping("/getAllClothes")
    public ResultDTO getAllClothes(){

        ResultDTO resultDTO = new ResultDTO();
        resultDTO.getClothesDTOList().addAll(clothesService.findAllClothesAndConvertToDTO());

        return resultDTO;
    }

    @PostMapping("/filter")
    public ResultDTO filterBySizesAndColors(@RequestBody FilterAPI data){

        ResultDTO resultDTO = new ResultDTO();

        List<Clothes> clothesFoundByParameter;
        List<ClothesDTO> clothesDTOList;

        if (data.getColors() == null && data.getSizes() == null){
            resultDTO.getClothesDTOList().addAll(clothesService.findAllClothesAndConvertToDTO());
            return resultDTO;
        }

        if (data.getSizes() == null){
            clothesFoundByParameter = clothesRepository.findAllByColors(data.getColors());
            clothesDTOList = new ArrayList<>();
            for (Clothes clothes : clothesFoundByParameter) {
                clothesDTOList.add(clothesService.setClothesDTO(clothes.getId()));
            }
            resultDTO.getClothesDTOList().addAll(clothesDTOList);
            return resultDTO;
        }

        if (data.getColors() == null){
            clothesFoundByParameter = clothesRepository.findAllBySizes(data.getSizes());
            clothesDTOList = new ArrayList<>();
            for (Clothes clothes : clothesFoundByParameter) {
                clothesDTOList.add(clothesService.setClothesDTO(clothes.getId()));
            }
            resultDTO.getClothesDTOList().addAll(clothesDTOList);
            return resultDTO;
        }

        clothesFoundByParameter = clothesService.findBySizesAndColors(data.getSizes(), data.getColors());
        clothesDTOList = new ArrayList<>();

        for (Clothes filteredClothes : clothesFoundByParameter) {
            clothesDTOList.add(clothesService.setClothesDTO(filteredClothes.getId()));
        }

        resultDTO.getClothesDTOList().addAll(clothesDTOList);
        return resultDTO;
    }

}
