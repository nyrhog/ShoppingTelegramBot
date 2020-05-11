package com.nyrhog.telegramShopping.entity.DTO;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResultDTO {

    private ResultForDTO result;

    private String message;

    private List<ClothesDTO> clothesDTOList = new ArrayList<>();

}
