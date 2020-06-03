package com.nyrhog.telegramShopping.repository;


import com.nyrhog.telegramShopping.entity.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    Clothes findByName(String name);

    @Query("FROM Clothes as clothes left join fetch clothes.colors as ccolor where " +
            "ccolor.name in ?1")
    List<Clothes> findAllByColors(List<String> colors);

    @Query("FROM Clothes as clothes left join fetch clothes.sizes as csize where " +
            "csize.name in ?1")
    List<Clothes> findAllBySizes(List<String> sizes);

    boolean existsById(Long id);

    void deleteByName(String name);

}
