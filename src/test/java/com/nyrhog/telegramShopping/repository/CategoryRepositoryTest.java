package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
class CategoryRepositoryTest {

    private Category category1 = new Category("Jeans");
    private Category category2 = new Category("T-shirts");
    private Category category3 = new Category("Shoes");

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp(){
        categoryRepository.deleteAll();

        categoryRepository.saveAll(Arrays.asList(category1, category2, category3));
        categoryRepository.flush();
    }

    @Test
    void findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        assertEquals(3, categoryList.size());

        //Это Stream))) Очнь удобная штука
        assertTrue(categoryList.stream().anyMatch(category -> category.getName().equals(category1.getName())));
        assertTrue(categoryList.stream().anyMatch(category -> category.getName().equals(category2.getName())));
        assertTrue(categoryList.stream().anyMatch(category -> category.getName().equals(category3.getName())));
    }

    @Test
    void findByName() {
        Category category = categoryRepository.findByName(category1.getName());

        assertEquals(category1.getName(), category.getName());
    }
}