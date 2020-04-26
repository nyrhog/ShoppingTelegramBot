package com.nyrhog.telegramShopping.service;

import com.nyrhog.telegramShopping.repository.ClothesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class ClothesService {

    @Autowired
    private ClothesRepository clothesRepository;


}
