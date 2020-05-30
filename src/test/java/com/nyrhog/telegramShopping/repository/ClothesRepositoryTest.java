package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Category;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.Color;
import com.nyrhog.telegramShopping.entity.Size;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class ClothesRepositoryTest {

    private Clothes clothes;

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
        categoryRepository.deleteAll();
        clothes = new Clothes("Brand new jeans");
    }


    @Test
    @Order(1)
    void findByName(){
        clothesRepository.saveAndFlush(clothes);

        Clothes clothesDB = clothesRepository.findByName(clothes.getName());
        assertNotNull(clothesDB);
        assertEquals(clothes.getName(), clothesDB.getName());
    }


    @Test
    @Order(2)
    void updateNameOfClothes(){
        clothesRepository.saveAndFlush(clothes);

        Clothes clothesDB = clothesRepository.findByName(clothes.getName());
        clothesDB.setName("Brand new T-shirt");
        clothesRepository.saveAndFlush(clothesDB);

        Clothes clothesTest = clothesRepository.findByName(clothesDB.getName());
        assertNotNull(clothesDB);
        assertEquals(clothesDB.getName(), clothesTest.getName());
    }

    @Test
    @Order(3)
    void deleteClothes(){
        clothesRepository.saveAndFlush(clothes);

        clothesRepository.deleteById(clothesRepository.findByName(clothes.getName()).getId());
        clothesRepository.flush();

        assertNull(clothesRepository.findByName(clothes.getName()));
    }


    @Test
    @Order(4)
    void addClothesWithAllSideObjects(){
        clothes.addSize(new Size("L"));
        clothes.addColor(new Color("Green"));
        clothes.addCategory(new Category("Boots"));

        clothesRepository.saveAndFlush(clothes);

        Clothes clothesDB = clothesRepository.findByName(clothes.getName());

        assertNotNull(clothesDB);
        assertEquals("L", clothesDB.getSizes().get(0).getName());
        assertEquals("Green", clothesDB.getColors().get(0).getName());
        assertEquals("Boots", clothesDB.getCategories().get(0).getName());
    }

    @Test
    @Order(5)
    void editClothesWithAllSideObjects(){
        clothes.addSize(new Size("L"));
        clothes.addColor(new Color("Green"));
        clothes.addCategory(new Category("Boots"));

        clothesRepository.saveAndFlush(clothes);

        Clothes clothesDB = clothesRepository.findByName(clothes.getName());
        clothesRepository.flush();

        clothesDB.setName("Brand change Name");
        clothesDB.getSizes().get(0).setName("XL");
        clothesDB.getColors().get(0).setName("Black");
        clothesDB.getCategories().get(0).setName("Shoes");
        clothesRepository.saveAndFlush(clothesDB);

        Clothes clothesNew = clothesRepository.findByName(clothesDB.getName());

        assertNotNull(clothesNew);
        assertEquals(clothesDB.getName(), clothesNew.getName());
        assertEquals(clothesDB.getSizes().get(0).getName(), clothesNew.getSizes().get(0).getName());
        assertEquals(clothesDB.getColors().get(0).getName(), clothesNew.getColors().get(0).getName());
        assertEquals(clothesDB.getCategories().get(0).getName(), clothesNew.getCategories().get(0).getName());
    }

    @Test
    @Order(6)
    void removeClothesWithAllSideObjects(){
        clothes.addSize(new Size("L"));
        clothes.addColor(new Color("Green"));
        clothes.addCategory(new Category("Boots"));

        clothesRepository.saveAndFlush(clothes);

        clothesRepository.deleteByName(clothes.getName());
        clothesRepository.flush();

        assertNull(categoryRepository.findByName(clothes.getName()));
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
}