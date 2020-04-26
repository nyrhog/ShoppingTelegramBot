package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Color;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends CrudRepository<Color, Long> {

    List<Color> findAll();


}
