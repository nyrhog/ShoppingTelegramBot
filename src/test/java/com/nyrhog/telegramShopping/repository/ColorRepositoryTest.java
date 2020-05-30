package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Color;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class ColorRepositoryTest {

    private Color color1 = new Color("White");
    private Color color2 = new Color("Blue");
    private Color color3 = new Color("Red");

    @Autowired
    private ColorRepository colorRepository;

    @BeforeEach
    void setUp(){
        colorRepository.deleteAll();

        colorRepository.saveAll(Arrays.asList(color1, color2, color3));
        colorRepository.flush();
    }

    @Test
    @Order(1)
    void findAll() {
        List<Color> colorList = colorRepository.findAll();

        //Это Stream))) Очнь удобная штука
        assertTrue(colorList.stream().anyMatch(color -> color.getName().equals(color1.getName())));
        assertTrue(colorList.stream().anyMatch(color -> color.getName().equals(color2.getName())));
        assertTrue(colorList.stream().anyMatch(color -> color.getName().equals(color3.getName())));
    }

    @Test
    @Order(2)
    void findByName() {
        Color color = colorRepository.findByName(color1.getName());

        assertEquals(color1.getName(), color.getName());
    }
}