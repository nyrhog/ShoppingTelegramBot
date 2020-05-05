package com.nyrhog.telegramShopping.service;

import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.Color;
import com.nyrhog.telegramShopping.entity.DTO.ClothesDTO;
import com.nyrhog.telegramShopping.entity.Size;
import com.nyrhog.telegramShopping.repository.ClothesRepository;
import com.nyrhog.telegramShopping.repository.ColorRepository;
import com.nyrhog.telegramShopping.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClothesService {

    @Autowired
    private ClothesRepository clothesRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;


    public Clothes findByID(Long id){
        return clothesRepository.findById(id).orElse(null);
    }

    public ClothesDTO setClothesDTO(Long id){
        Clothes clothes = findByID(id);

        ClothesDTO clothesDTO = new ClothesDTO();

        if (clothes==null) return clothesDTO;

        List<String> colorNames = new ArrayList<>();
        List<Color> colors = colorRepository.findByClothes(clothes);
        for (Color x: colors) {
            colorNames.add(x.getName());
        }
        List<String> sizeNames = new ArrayList<>();
        List<Size> sizes = sizeRepository.findByClothes(clothes);
        for (Size s: sizes
             ) {
            sizeNames.add(s.getName());
        }

        clothesDTO.setId(id);
        clothesDTO.setName(clothes.getName());
        clothesDTO.setCategory(clothes.getCategory());
        clothesDTO.setPrice(clothes.getPrice());
        clothesDTO.setColors(colorNames);
        clothesDTO.setSizes(sizeNames);

        return clothesDTO;
    }

    public Clothes getClothesFromDTO(ClothesDTO clothesDTO){
        return findByID(clothesDTO.getId());
    }
}
