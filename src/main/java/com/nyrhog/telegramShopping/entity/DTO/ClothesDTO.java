package com.nyrhog.telegramShopping.entity.DTO;

import com.nyrhog.telegramShopping.entity.Category;
import com.nyrhog.telegramShopping.entity.Color;
import com.nyrhog.telegramShopping.entity.Size;
import com.nyrhog.telegramShopping.repository.ClothesRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
public class ClothesDTO {

    private ResultForDTO result;
    private Long id;
    private String name;
    private Double price;
    private Category category;
    private List<String> colors;
    private List<String> sizes;

}
