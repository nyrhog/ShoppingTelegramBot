package com.nyrhog.telegramShopping.service;

import com.nyrhog.telegramShopping.entity.Client;
import com.nyrhog.telegramShopping.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
}
