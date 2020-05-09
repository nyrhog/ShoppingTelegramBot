package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Category;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.Color;
import com.nyrhog.telegramShopping.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {
    List<Clothes> findAll();

    List<Clothes> findAllByColors(Color color);

    List<Clothes> findAllBySizes(Size size);

    Clothes findByName(String name);

    List<Clothes> findByNameIn(List<String> names);

    @Query("FROM Clothes as clothes left join fetch clothes.colors as ccolor where " +
            "ccolor.name in ?1")
    List<Clothes> findAllByColorsIn(List<String> colors);

    @Query("FROM Clothes as clothes left join fetch clothes.sizes as csize where " +
            "csize in ?1")
    List<Clothes> findAllBySizes(List<Size> sizes);

}
