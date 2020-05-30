package com.nyrhog.telegramShopping.entity.DTO;

import com.nyrhog.telegramShopping.entity.Category;
import com.nyrhog.telegramShopping.entity.Color;
import com.nyrhog.telegramShopping.entity.Size;
import com.nyrhog.telegramShopping.repository.ClothesRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class ClothesDTO{

    private Long id;
    private String name;
    private Double price;

    private List<String> categories = new ArrayList<>();

    public void addCategory(String category){
        categories.add(category);
    }

    public void removeCategory(String category){
        categories.remove(category);
    }

    private List<String> colors = new ArrayList<>();

    public void addColors(String color){
        colors.add(color);
    }

    public void removeColors(String color){
        colors.remove(color);
    }

    private List<String> sizes = new ArrayList<>();

    public void addSize(String size){
        sizes.add(size);
    }

    public void removeSize(String size){
        sizes.remove(size);
    }


}
