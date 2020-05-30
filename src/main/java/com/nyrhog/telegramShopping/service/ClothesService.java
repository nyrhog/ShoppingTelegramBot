package com.nyrhog.telegramShopping.service;

import com.nyrhog.telegramShopping.entity.Category;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.Color;
import com.nyrhog.telegramShopping.entity.DTO.ClothesDTO;
import com.nyrhog.telegramShopping.entity.DTO.ResultDTO;
import com.nyrhog.telegramShopping.entity.DTO.ResultForDTO;
import com.nyrhog.telegramShopping.entity.Size;
import com.nyrhog.telegramShopping.repository.ClothesFetchRepository;
import com.nyrhog.telegramShopping.repository.ClothesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ClothesService {

    @Autowired
    private ClothesFetchRepository clothesFetchRepository;

    @Autowired
    private ClothesRepository clothesRepository;


    public ResultDTO getOneClothesById(Long id){

        ResultDTO resultDTO = new ResultDTO();

        Clothes clothes = clothesFetchRepository.getOneClothesById(id);

        if (clothes != null){
            resultDTO.setResult(ResultForDTO.SUCCESS);
            resultDTO.addClothesDTO(converterClothesToDTO(clothes));
        } else {
            resultDTO.setResult(ResultForDTO.ERROR);
            resultDTO.setMessage("Невозможно найти одежду по такому ID");
        }

        return resultDTO;
    }

    public ResultDTO getAllClothes(){

        ResultDTO resultDTO = new ResultDTO();
        List<Clothes> clothes = clothesFetchRepository.getAllClothes();

        if (clothes.size() != 0){
            resultDTO.setResult(ResultForDTO.SUCCESS);
            for (Clothes clothesItem : clothes) {
                resultDTO.addClothesDTO(converterClothesToDTO(clothesItem));
            }
        } else {
            resultDTO.setResult(ResultForDTO.ERROR);
            resultDTO.setMessage("Список одежды пуст");
        }

        return resultDTO;
    }

    public ResultDTO getClothesByFilter(List<String> category, List<String> sizes, List<String> colors){

        ResultDTO resultDTO = new ResultDTO();
        List<Clothes> clothes = new ArrayList<>(clothesFetchRepository.getClothesByFilter(category, sizes, colors));

        if (clothes.size() != 0){
            resultDTO.setResult(ResultForDTO.SUCCESS);
            for (Clothes clothesItem : clothes) {
                resultDTO.addClothesDTO(converterClothesToDTO(clothesItem));
            }
        } else {
            resultDTO.setResult(ResultForDTO.ERROR);
            resultDTO.setMessage("Список одежды пуст");
        }

        return resultDTO;
    }





    /*
    Конвертер из Clothes в ClothesDTO
     */
    public ClothesDTO converterClothesToDTO(Clothes clothes){
        ClothesDTO clothesDTO = new ClothesDTO();
        clothesDTO.setId(clothes.getId());
        clothesDTO.setName(clothes.getName());
        clothesDTO.setPrice(clothes.getPrice());

        clothes.getCategories().forEach(category -> clothesDTO.addCategory(category.getName()));
        clothes.getSizes().forEach(size -> clothesDTO.addSize(size.getName()));
        clothes.getColors().forEach(color -> clothesDTO.addColors(color.getName()));

        return clothesDTO;
    }
}