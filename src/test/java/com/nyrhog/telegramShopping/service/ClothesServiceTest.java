package com.nyrhog.telegramShopping.service;

import com.nyrhog.telegramShopping.entity.Category;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.Color;
import com.nyrhog.telegramShopping.entity.DTO.ClothesDTO;
import com.nyrhog.telegramShopping.entity.DTO.ResultDTO;
import com.nyrhog.telegramShopping.entity.DTO.ResultForDTO;
import com.nyrhog.telegramShopping.entity.Size;
import com.nyrhog.telegramShopping.repository.CategoryRepository;
import com.nyrhog.telegramShopping.repository.ClothesRepository;
import com.nyrhog.telegramShopping.repository.ColorRepository;
import com.nyrhog.telegramShopping.repository.SizeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class ClothesServiceTest {

    private Category category1 = new Category("T-Shirts");
    private Category category2 = new Category("Shorts");
    private Category category3 = new Category("Shoes");
    private Size size1 = new Size("S");
    private Size size2 = new Size("M");
    private Size size3 = new Size("L");
    private Color color1 = new Color("White");
    private Color color2 = new Color("Blue");
    private Color color3 = new Color("Red");

    @Autowired
    private ClothesService clothesService;

    @Autowired
    private ClothesRepository clothesRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @BeforeEach
    void setUp(){
        clothesRepository.deleteAll();
    }

    @Test
    @Order(1)
    void converterClothesToDTO() {

        Clothes clothes = new Clothes();
        clothes.setId(clothes.getId());
        clothes.setName("Big Brand New");
        clothes.addSize(new Size("L"));
        clothes.addSize(new Size("XL"));
        clothes.addColor(new Color("Green"));
        clothes.addColor(new Color("Blue"));
        clothes.addCategory(new Category("Boots"));
        clothes.addCategory(new Category("Shoes"));

        ClothesDTO clothesDTO = clothesService.converterClothesToDTO(clothes);

        assertNotNull(clothesDTO);
        assertEquals(clothes.getId(), clothesDTO.getId(), "Проверяем ID");
        assertEquals(clothes.getName(), clothesDTO.getName(), "Проверяем имя");

        assertEquals(clothes.getCategories().size(), clothesDTO.getCategories().size(), "Проверяем, что массивы с категориями одежды одинакого размера");
        assertTrue(clothesDTO.getCategories().contains(clothes.getCategories().get(0).getName()), " Проверяем, что 0 элемент массива с категорями одежды имеется в другом массиве");
        assertTrue(clothesDTO.getCategories().contains(clothes.getCategories().get(1).getName()), " Проверяем, что 1 элемент массива с категорями одежды имеется в другом массиве");

        assertEquals(clothes.getSizes().size(), clothesDTO.getSizes().size(), "Проверяем, что массивы с размерами одежды одинакого размера");
        assertTrue(clothesDTO.getSizes().contains(clothes.getSizes().get(0).getName()), " Проверяем, что 0 элемент массива с размерами одежды имеется в другом массиве");
        assertTrue(clothesDTO.getSizes().contains(clothes.getSizes().get(1).getName()), " Проверяем, что 1 элемент массива с размерами одежды имеется в другом массиве");

        assertEquals(clothes.getColors().size(), clothesDTO.getColors().size(), "Проверяем, что массивы с цветами одежды одинакого размера");
        assertTrue(clothesDTO.getColors().contains(clothes.getColors().get(0).getName()), " Проверяем, что 0 элемент массива с цветами одежды имеется в другом массиве");
        assertTrue(clothesDTO.getColors().contains(clothes.getColors().get(1).getName()), " Проверяем, что 1 элемент массива с цветами одежды имеется в другом массиве");
    }


    @Test
    @Order(2)
    void getOneClothesById(){

        //Сохраняем данные
        Clothes clothes = new Clothes("Brand New Jeans");
        clothes.setPrice(100d);
        clothes.addSize(new Size("L"));
        clothes.addColor(new Color("Green"));
        clothes.addCategory(new Category("Boots"));
        clothesRepository.saveAndFlush(clothes);

        //Берем одежду по Name, чтобы получить ID
        Long id = clothesRepository.findByName(clothes.getName()).getId();
        clothesRepository.flush();

        //Сохраняем для проверки
        clothes.setId(id);

        //Берем одежду и конвертируем его в результат
        ResultDTO resultDTO = clothesService.getOneClothesById(id);
        clothesRepository.flush();

        assertEquals(ResultForDTO.SUCCESS, resultDTO.getResult());
        assertEquals(clothes.getId(), resultDTO.getClothesDTOList().get(0).getId());
        assertEquals(clothes.getName(), resultDTO.getClothesDTOList().get(0).getName());
        assertEquals(clothes.getPrice(), resultDTO.getClothesDTOList().get(0).getPrice());


        assertEquals(clothes.getCategories().size(), resultDTO.getClothesDTOList().get(0).getCategories().size(), "Проверяем, что массивы с категориями одежды одинакого размера");
        assertEquals(clothes.getCategories().get(0).getName(), resultDTO.getClothesDTOList().get(0).getCategories().get(0), " Проверяем, что 0 элемент массива с категорями одежды имеется в другом массиве");

        assertEquals(clothes.getSizes().size(), resultDTO.getClothesDTOList().get(0).getSizes().size(), "Проверяем, что массивы с размерами одежды одинакого размера");
        assertEquals(clothes.getSizes().get(0).getName(), resultDTO.getClothesDTOList().get(0).getSizes().get(0), " Проверяем, что 0 элемент массива с размерами одежды имеется в другом массиве");

        assertEquals(clothes.getColors().size(), resultDTO.getClothesDTOList().get(0).getColors().size(), "Проверяем, что массивы с цветами одежды одинакого размера");
        assertEquals(clothes.getColors().get(0).getName(), resultDTO.getClothesDTOList().get(0).getColors().get(0), " Проверяем, что 0 элемент массива с цветами одежды имеется в другом массиве");
    }


    @Test
    @Order(3)
    void getAllClothes(){

        //Сохраняем данные
        Clothes clothes = new Clothes("Brand New Jeans");
        clothes.setPrice(100d);
        clothes.addSize(new Size("L"));
        clothes.addSize(new Size("XL"));
        clothes.addColor(new Color("Green"));
        clothes.addCategory(new Category("Boots"));

        Clothes clothes2 = new Clothes("Marko");
        clothes2.setPrice(100d);
        clothes2.addSize(new Size("L"));
        clothes2.addColor(new Color("Black"));
        clothes2.addColor(new Color("Pink"));
        clothes2.addCategory(new Category("Boots"));
        clothesRepository.saveAll(Arrays.asList(clothes, clothes2));
        clothesRepository.flush();


        //Берем одежду и конвертируем его в результат
        ResultDTO resultDTO = clothesService.getAllClothes();
        clothesRepository.flush();


        //Тестируем
        assertEquals(ResultForDTO.SUCCESS, resultDTO.getResult());
        assertEquals(2, resultDTO.getClothesDTOList().size());
    }


    private void setUpAllClothes(){

        sizeRepository.deleteAll();
        colorRepository.deleteAll();
        categoryRepository.deleteAll();

        // Категория одежды
        categoryRepository.saveAll(Arrays.asList(category1, category2, category3));
        categoryRepository.flush();

        // Размеры одежды
        sizeRepository.saveAll(Arrays.asList(size1, size2, size3));
        sizeRepository.flush();


        //  Цвета одежды
        colorRepository.saveAll(Arrays.asList(color1, color2, color3));
        colorRepository.flush();

        //Добавление Одежды
        Clothes clothes1 = new Clothes("Hugo Boss T-shirt");
        clothes1.setPrice(200d);
        clothes1.addCategory(categoryRepository.findByName(category1.getName()));
        clothes1.addSize(sizeRepository.findByName(size1.getName()));
        clothes1.addColor(colorRepository.findByName(color3.getName()));
        categoryRepository.flush();
        sizeRepository.flush();
        colorRepository.flush();
        clothesRepository.saveAndFlush(clothes1);

        Clothes clothes2 = new Clothes("Diesel Shorts");
        clothes2.setPrice(300d);
        clothes2.addCategory(categoryRepository.findByName(category2.getName()));
        clothes2.addSize(sizeRepository.findByName(size2.getName()));
        clothes2.addSize(sizeRepository.findByName(size1.getName()));
        clothes2.addColor(colorRepository.findByName(color1.getName()));
        clothes2.addColor(colorRepository.findByName(color3.getName()));
        categoryRepository.flush();
        sizeRepository.flush();
        colorRepository.flush();
        clothesRepository.saveAndFlush(clothes2);

        Clothes clothes3 = new Clothes("Adidas Shoes");
        clothes3.setPrice(400d);
        clothes3.addCategory(categoryRepository.findByName(category3.getName()));
        clothes3.addSize(sizeRepository.findByName(size2.getName()));
        clothes3.addColor(colorRepository.findByName(color3.getName()));
        categoryRepository.flush();
        sizeRepository.flush();
        colorRepository.flush();
        clothesRepository.saveAndFlush(clothes3);
    }

    @Test
    @Order(4)
    @Transactional
    void getClothesByFilter(){

        setUpAllClothes();

        ResultDTO resultDTO = clothesService.getClothesByFilter(Arrays.asList(category1.getName(), category2.getName()), Collections.singletonList(size1.getName()), Collections.singletonList(color3.getName()));

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(resultDTO.toString());
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
//        clothes1.addColor(colorBlue);
//        clothes1.addSize(sizeL);
//
//        Clothes clothes3 = new Clothes("Clothes3");
//        clothes1.addColor(colorRed);
//        clothes1.addSize(sizeXL);
//
//        Clothes clothes4 = new Clothes("Clothes4");
//        clothes1.addColor(colorPink);
//        clothes1.addSize(sizeS);
//
//        clothesRepository.saveAll(Arrays.asList(clothes1, clothes2, clothes3, clothes4));
//        clothesRepository.flush();
//
//        List<String> colors = new ArrayList<>();
//        colors.add(colorBlue.getName());
//        colors.add(colorRed.getName());
//
//        List<Size> sizes = new ArrayList<>();
//        sizes.add(sizeL);
//        sizes.add(sizeXL);
//
////        List<Clothes> clothes = clothesRepository.findAllBySizes(sizes);
//
////        System.out.println(clothes.size());
//
////        for (Clothes c : clothes) {
////            System.out.println(c.getName());
//
////        }
//    }
}