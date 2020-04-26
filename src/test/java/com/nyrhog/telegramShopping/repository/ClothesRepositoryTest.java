package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Category;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.Color;
import com.nyrhog.telegramShopping.entity.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClothesRepositoryTest {

    @Autowired
    private ClothesRepository clothesRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ColorRepository colorRepository;

    @BeforeEach
    void setUp(){
        clothesRepository.deleteAll();
        colorRepository.deleteAll();
        sizeRepository.deleteAll();
    }

    @Test
    void saveClothes(){
        Clothes clothes = new Clothes();
        clothes.setName("name1");
        clothes.setCategory(Category.BOOTS);

        clothesRepository.save(clothes);

        assertEquals(1, clothesRepository.findAll().size());
    }

    @Test
    void updateNameOfClothes(){
        Clothes clothes = new Clothes();
        clothes.setName("name1");
        clothes.setCategory(Category.BOOTS);
        clothesRepository.save(clothes);


        List<Clothes> clothesList = clothesRepository.findAll();
        clothesList.get(0).setName("changedName");
        clothesRepository.save(clothesList.get(0));
        clothesList = clothesRepository.findAll();

        assertEquals("changedName", clothesList.get(0).getName());
    }

    @Test
    void deleteClothes(){
        Clothes clothes = new Clothes();
        clothes.setName("name1");
        clothes.setCategory(Category.BOOTS);

        clothesRepository.save(clothes);
        clothesRepository.delete(clothes);

        assertEquals(0, clothesRepository.findAll().size());
    }

    @Test
    void deleteClothesWithoutSizeAndColor(){
        Size size = new Size();
        size.setName("L");
        Size size1 = new Size();
        size1.setName("S");

        Color color = new Color();
        color.setName("Pink");
        Color color1 = new Color();
        color1.setName("Black");

        Clothes clothes = new Clothes();
        clothes.setName("Clothes");
        clothes.setCategory(Category.BOOTS);
        clothes.addColor(color);
        clothes.addColor(color1);
        clothes.addSize(size);
        clothes.addSize(size1);

        clothesRepository.save(clothes);
        
        clothesRepository.delete(clothes);

        assertEquals(0, clothesRepository.findAll().size());
        assertEquals(2, colorRepository.findAll().size());
        assertEquals(2, sizeRepository.findAll().size());
    }
}