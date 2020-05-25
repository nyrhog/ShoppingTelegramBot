package com.nyrhog.telegramShopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@SpringBootApplication
public class TelegramShoppingApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramShoppingApplication.class, args);
    }
}