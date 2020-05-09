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
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;



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

    @Autowired
    private CategoryRepository categoryRepository;

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
        assertNotNull(clothesRepository.findByName("name1"));
    }

    @Test
    void updateNameOfClothes(){
        Clothes clothes = new Clothes("name1");

        clothesRepository.saveAndFlush(clothes);

        clothes = clothesRepository.findByName("name1");
        clothes.setName("changedName");
        clothesRepository.saveAndFlush(clothes);

        assertNotNull(clothesRepository.findByName("changedName"));
    }

    @Test
    void deleteClothes(){
        Clothes clothes = new Clothes("name1");

        clothesRepository.saveAndFlush(clothes);
        clothesRepository.deleteById(clothesRepository.findByName("name1").getId());
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
        clothesRepository.deleteById(clothesRepository.findByName("Clothes").getId());
        clothesRepository.flush();

        assertEquals(0, clothesRepository.findAll().size());
        assertEquals(2, colorRepository.findAll().size());
        assertEquals(2, sizeRepository.findAll().size());
    }

    @Test
    void addOneFullClothes(){
        Clothes clothes = new Clothes("Test");
        clothes.addSize(new Size("L"));
        clothes.addColor(new Color("Green"));
        clothes.addCategory(new Category("Boots"));

        clothesRepository.saveAndFlush(clothes);

        assertEquals(1, clothesRepository.findAll().size());

    }

    @Test
    void findClothesByColorsAndSizes(){

        Color colorBlue = new Color("Blue");
        Color colorRed = new Color("Red");
        Color colorPink = new Color("Pink");

        Size sizeL = new Size("L");
        Size sizeXL = new Size("XL");
        Size sizeS = new Size("S");

        Clothes clothes1 = new Clothes("Clothes1");
        clothes1.addColor(colorBlue);
        clothes1.addSize(sizeXL);

        Clothes clothes2 = new Clothes("Clothes2");
        clothes1.addColor(colorBlue);
        clothes1.addSize(sizeL);

        Clothes clothes3 = new Clothes("Clothes3");
        clothes1.addColor(colorRed);
        clothes1.addSize(sizeXL);

        Clothes clothes4 = new Clothes("Clothes4");
        clothes1.addColor(colorPink);
        clothes1.addSize(sizeS);

        clothesRepository.saveAll(Arrays.asList(clothes1, clothes2, clothes3, clothes4));
        clothesRepository.flush();

        List<String> colors = new ArrayList<>();
        colors.add(colorBlue.getName());
        colors.add(colorRed.getName());

        List<Size> sizes = new ArrayList<>();
        sizes.add(sizeL);
        sizes.add(sizeXL);

        List<Clothes> clothes = clothesRepository.findAllBySizes(sizes);

        System.out.println(clothes.size());

        for (Clothes c : clothes) {
            System.out.println(c.getName());

        }
    }
}