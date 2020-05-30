package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Size;
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
class SizeRepositoryTest {

    private Size size1 = new Size("L");
    private Size size2 = new Size("M");
    private Size size3 = new Size("XL");

    @Autowired
    private SizeRepository sizeRepository;

    @BeforeEach
    void setUp(){
        sizeRepository.deleteAll();

        sizeRepository.saveAll(Arrays.asList(size1, size2, size3));
        sizeRepository.flush();
    }

    @Test
    @Order(1)
    void findAll(){
        List<Size> sizeList = sizeRepository.findAll();
        assertEquals(3, sizeList.size());

        //Это Stream))) Очнь удобная штука
        assertTrue(sizeList.stream().anyMatch(size -> size.getName().equals(size1.getName())));
        assertTrue(sizeList.stream().anyMatch(size -> size.getName().equals(size2.getName())));
        assertTrue(sizeList.stream().anyMatch(size -> size.getName().equals(size3.getName())));
    }

    @Test
    @Order(2)
    void findByName(){
        Size size = sizeRepository.findByName(size1.getName());

        assertEquals(size1.getName(), size.getName());
    }

}