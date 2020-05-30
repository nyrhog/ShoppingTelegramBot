package com.nyrhog.telegramShopping.entity.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResultDTO {

    private ResultForDTO result;

    private String message;

    @JsonProperty("clothes")
    private List<ClothesDTO> clothesDTOList = new ArrayList<>();

    public void addClothesDTO(ClothesDTO clothesDTO){
        clothesDTOList.add(clothesDTO);
    }

    public void removeClothesDTO(ClothesDTO clothesDTO){
        clothesDTOList.remove(clothesDTO);
    }

}
