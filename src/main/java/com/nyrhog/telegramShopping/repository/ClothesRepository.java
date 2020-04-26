package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Category;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.Color;
import com.nyrhog.telegramShopping.entity.Size;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothesRepository extends CrudRepository<Clothes, Long> {
    List<Clothes> findAll();

    List<Clothes> findAllByCategory(Category category);

    List<Clothes> findAllByColors(Color color);

    List<Clothes> findAllBySizes(Size size);
}
