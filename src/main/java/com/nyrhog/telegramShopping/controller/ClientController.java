package com.nyrhog.telegramShopping.controller;


import com.nyrhog.telegramShopping.entity.Client;
import com.nyrhog.telegramShopping.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/getAll")
    public List<Client> getAll(){
        return clientRepository.findAll();
    }

}
