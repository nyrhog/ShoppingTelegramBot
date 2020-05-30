package com.nyrhog.telegramShopping.controller;

import com.nyrhog.telegramShopping.entity.API.FilterAPI;
import com.nyrhog.telegramShopping.entity.DTO.ResultDTO;
import com.nyrhog.telegramShopping.entity.DTO.ResultForDTO;
import com.nyrhog.telegramShopping.service.ClothesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClothesController {

    @Autowired
    private ClothesService clothesService;

    @GetMapping("/getOneClothes/{id:\\d+}")
    public ResultDTO getOneClothes(@PathVariable Long id){
        return clothesService.getOneClothesById(id);
    }

    @GetMapping("/getAllClothes")
    public ResultDTO getAllClothes(){
        return clothesService.getAllClothes();
    }

//    {
//        "category": [
//        "test"
//  ],
//        "size": [
//        "L",
//                "XL"
//  ],
//        "color": [
//        "Blue",
//                "Green"
//  ]
//    }

    @PostMapping("/getClothesByFilter")
    public ResultDTO getClothesByFilter(@RequestBody FilterAPI data){

        ResultDTO resultDTO = new ResultDTO();

        if(data == null || (data.getColors() == null && data.getSizes() == null &&  data.getCategories() == null)){
            resultDTO.setResult(ResultForDTO.ERROR);
            resultDTO.setMessage("Фильтр для одежды не задан");
            return resultDTO;
        } else {
            resultDTO = clothesService.getClothesByFilter(data.getCategories(), data.getSizes(), data.getColors());
        }

        return resultDTO;
    }
}

//
//    @PostMapping("/filter")
//    public ResultDTO filterBySizesAndColors(@RequestBody FilterAPI data){
//
//        ResultDTO resultDTO = new ResultDTO();
//
//        List<Clothes> clothesFoundByParameter;
//        List<ClothesDTO> clothesDTOList;
//
//        if (data.getColors() == null && data.getSizes() == null){
//            resultDTO.getClothesDTOList().addAll(clothesService.findAllClothesAndConvertToDTO());
//            return resultDTO;
//        }
//
//        if (data.getSizes() == null){
//            clothesFoundByParameter = clothesRepository.findAllByColors(data.getColors());
//            clothesDTOList = new ArrayList<>();
//            for (Clothes clothes : clothesFoundByParameter) {
//                clothesDTOList.add(clothesService.setClothesDTO(clothes.getId()));
//            }
//            resultDTO.getClothesDTOList().addAll(clothesDTOList);
//            return resultDTO;
//        }
//
//        if (data.getColors() == null){
//            clothesFoundByParameter = clothesRepository.findAllBySizes(data.getSizes());
//            clothesDTOList = new ArrayList<>();
//            for (Clothes clothes : clothesFoundByParameter) {
//                clothesDTOList.add(clothesService.setClothesDTO(clothes.getId()));
//            }
//            resultDTO.getClothesDTOList().addAll(clothesDTOList);
//            return resultDTO;
//        }
//
//        clothesFoundByParameter = clothesService.findBySizesAndColors(data.getSizes(), data.getColors());
//        clothesDTOList = new ArrayList<>();
//
//        for (Clothes filteredClothes : clothesFoundByParameter) {
//            clothesDTOList.add(clothesService.setClothesDTO(filteredClothes.getId()));
//        }
//
//        resultDTO.getClothesDTOList().addAll(clothesDTOList);
//        return resultDTO;
//    }
//
//}
