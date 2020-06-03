package com.nyrhog.telegramShopping.entity.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResultDTO extends DTO {

    @JsonProperty("clothes")
    private List<ClothesDTO> clothesDTOList = new ArrayList<>();

    public void addClothesDTO(ClothesDTO clothesDTO){
        clothesDTOList.add(clothesDTO);
    }

    public void removeClothesDTO(ClothesDTO clothesDTO){
        clothesDTOList.remove(clothesDTO);
    }

}
