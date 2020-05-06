package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Category;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.Color;
import com.nyrhog.telegramShopping.entity.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
        Clothes clothes = new Clothes("name1");

        clothesRepository.saveAndFlush(clothes);

        assertEquals(1, clothesRepository.findAll().size());
    }

    @Test
    void updateNameOfClothes(){
        Clothes clothes = new Clothes("name1");

        clothesRepository.saveAndFlush(clothes);


        List<Clothes> clothesList = clothesRepository.findAll();
        clothesList.get(0).setName("changedName");
        clothesRepository.saveAndFlush(clothesList.get(0));

        assertEquals("changedName", clothesRepository.findAll().get(0).getName());
    }

    @Test
    void deleteClothes(){
        Clothes clothes = new Clothes("name1");

        clothesRepository.saveAndFlush(clothes);
        clothesRepository.delete(clothes);
        clothesRepository.flush();

        assertEquals(0, clothesRepository.findAll().size());
    }

    @Test
    void deleteClothesWithoutSizeAndColor(){
        Size size = new Size("L");
        Size size1 = new Size("S");

        Color color = new Color("Pink");
        Color color1 = new Color("Black");

        Clothes clothes = new Clothes("Clothes");
        clothes.addColor(color);
        clothes.addColor(color1);
        clothes.addSize(size);
        clothes.addSize(size1);

        clothesRepository.saveAndFlush(clothes);
        clothesRepository.delete(clothes);
        clothesRepository.flush();

        assertEquals(0, clothesRepository.findAll().size());
        assertEquals(2, colorRepository.findAll().size());
        assertEquals(2, sizeRepository.findAll().size());
    }

    @Test
    void addOneFullClothes(){
        Clothes clothes = new Clothes("Test");

        Color color = new Color("Green");
        List<Color> colors = new ArrayList<>();
        colors.add(color);

        Size size = new Size("L");
        List<Size> sizes = new ArrayList<>();
        sizes.add(size);

        clothes.setColors(colors);
        clothes.setSizes(sizes);
        clothes.setPrice(123123d);

        clothesRepository.saveAndFlush(clothes);
    }
}