package com.nyrhog.telegramShopping.service;

import com.nyrhog.telegramShopping.entity.Category;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.Color;
import com.nyrhog.telegramShopping.entity.DTO.ClothesDTO;
import com.nyrhog.telegramShopping.entity.Size;
import com.nyrhog.telegramShopping.repository.CategoryRepository;
import com.nyrhog.telegramShopping.repository.ClothesRepository;
import com.nyrhog.telegramShopping.repository.ColorRepository;
import com.nyrhog.telegramShopping.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CategoryRepository categoryRepository;


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
        for (Size s: sizes) {
            sizeNames.add(s.getName());
        }

        List<String> categoryNames = new ArrayList<>();
        List<Category> categories = categoryRepository.findByClothes(clothes);
        for (Category s: categories) {
            categoryNames.add(s.getName());
        }

        clothesDTO.setId(id);
        clothesDTO.setName(clothes.getName());
        clothesDTO.setPrice(clothes.getPrice());
        clothesDTO.setColors(colorNames);
        clothesDTO.setSizes(sizeNames);
        clothesDTO.setCategories(categoryNames);

        return clothesDTO;
    }

    public Clothes getClothesFromDTO(ClothesDTO clothesDTO){
        return findByID(clothesDTO.getId());
    }

    public List<ClothesDTO> findAllClothesAndConvertToDTO(){
        List<Clothes> clothes = clothesRepository.findAll();
        List<ClothesDTO> clothesDTOs = new ArrayList<>();
        for (Clothes c: clothes) {
            clothesDTOs.add(setClothesDTO(c.getId()));
        }
        return clothesDTOs;
    }

    public List<Clothes> findBySizesAndColors(List<String> sizes, List<String> colors){

        List<Clothes> foundBySizes = clothesRepository.findAllBySizes(sizes);
        List<Clothes> common = new ArrayList<>();
        for (Clothes clothesItem : clothesRepository.findAllByColors(colors)) {
            if(!common.contains(clothesItem) && foundBySizes.contains(clothesItem))
                common.add(clothesItem);
        }

        return common;
    }
}
