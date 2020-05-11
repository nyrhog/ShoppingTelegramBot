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

//    @BeforeEach
//    void setUp(){
//        clothesRepository.deleteAll();
//        colorRepository.deleteAll();
//        sizeRepository.deleteAll();
//    }

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

//    @Test
//    void findClothesByColorsAndSizes(){
//
//        Color colorBlue = new Color("Blue");
//        Color colorRed = new Color("Red");
//        Color colorPink = new Color("Pink");
//
//        Size sizeL = new Size("L");
//        Size sizeXL = new Size("XL");
//        Size sizeS = new Size("S");
//
//        Clothes clothes1 = new Clothes("Clothes1");
//        clothes1.addColor(colorBlue);
//        clothes1.addSize(sizeXL);
//
//        Clothes clothes2 = new Clothes("Clothes2");
//        clothes2.addColor(colorBlue);
//        clothes2.addSize(sizeL);
//
//        Clothes clothes3 = new Clothes("Clothes3");
//        clothes3.addColor(colorRed);
//        clothes3.addSize(sizeXL);
//
//        Clothes clothes4 = new Clothes("Clothes4");
//        clothes4.addColor(colorPink);
//        clothes4.addSize(sizeS);
//
//        clothesRepository.saveAll(Arrays.asList(clothes1, clothes2, clothes3, clothes4));
//        clothesRepository.flush();
//
//        List<Color> colors = new ArrayList<>();
//        colors.add(colorPink);
//        colors.add(colorRed);
//
//        List<Size> sizes = new ArrayList<>();
//        sizes.add(sizeS);
//        sizes.add(sizeXL);
//
//        List<Clothes> clothes = clothesRepository.findAllBySizes(sizes);
//        clothes.addAll(clothesRepository.findAllByColorsIn(colors));
//
//        System.out.println(clothes.size());
//
//        for (Clothes c : clothes) {
//            System.out.println(c.getName());
//
//        }
//    }
    @Test
    void findClothesByColorsAndSizes2() {

        Color colorBlue = new Color("Blue");
        Color colorRed = new Color("Red");
        Color colorPink = new Color("Pink");
        colorRepository.saveAll(Arrays.asList(colorBlue, colorPink, colorRed));
        colorRepository.flush();

        Size sizeL = new Size("L");
        Size sizeXL = new Size("XL");
        Size sizeS = new Size("S");
        sizeRepository.saveAll(Arrays.asList(sizeL, sizeS, sizeXL));
        sizeRepository.flush();

        Clothes clothes1 = new Clothes("Clothes1");
        clothes1.addColor(colorRepository.findByName(colorBlue.getName()));
        clothes1.addSize(sizeRepository.findByName(sizeS.getName()));

        Clothes clothes2 = new Clothes("Clothes2");
        clothes2.addColor(colorRepository.findByName(colorBlue.getName()));
        clothes2.addColor(colorRepository.findByName(colorRed.getName()));
        clothes2.addSize(sizeRepository.findByName(sizeL.getName()));

        Clothes clothes3 = new Clothes("Clothes3");
        clothes3.addColor(colorRepository.findByName(colorRed.getName()));
        clothes3.addSize(sizeRepository.findByName(sizeXL.getName()));

        Clothes clothes4 = new Clothes("Clothes4");
        clothes4.addColor(colorRepository.findByName(colorPink.getName()));
        clothes4.addSize(sizeRepository.findByName(sizeS.getName()));

        Clothes clothes5 = new Clothes("Clothes5");
        clothes5.addColor(colorRepository.findByName(colorPink.getName()));
        clothes5.addSize(sizeRepository.findByName(sizeL.getName()));


        clothesRepository.saveAll(Arrays.asList(clothes1, clothes2, clothes3, clothes4, clothes5));
        clothesRepository.flush();

        for (Clothes clothes : clothesRepository.findAll()) {
            System.out.println("название: " + clothes.getName());
            System.out.println("цвет: " + clothes.getColors().get(0).getName());
            System.out.println("размер: " + clothes.getSizes().get(0).getName());
            System.out.println();
        }

        System.out.println();
        System.out.println();
        System.out.println();

        List<String> colors = new ArrayList<>();
        colors.add(colorBlue.getName());
        colors.add(colorRed.getName());

        List<String> sizes = new ArrayList<>();
        sizes.add(sizeL.getName());
        sizes.add(sizeXL.getName());

        List<Clothes> clothes = clothesRepository.findAllBySizes(sizes);
        List<Clothes> common = new ArrayList<>();
        for (Clothes clothesItem : clothesRepository.findAllByColors(colors)) {
            if (!common.contains(clothesItem) && clothes.contains(clothesItem)) {
                common.add(clothesItem);
            }
        }

        System.out.println(common.size());

        System.out.println();
        for (Clothes clothesItem : common) {
            System.out.println("название: " + clothesItem.getName());
            System.out.println("цвет: " + clothesItem.getColors().size());
            System.out.println("размер: " + clothesItem.getSizes().size());
            System.out.println();
        }
    }
}