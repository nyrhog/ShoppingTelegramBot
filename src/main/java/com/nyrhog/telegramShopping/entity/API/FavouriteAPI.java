package com.nyrhog.telegramShopping.entity.API;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FavouriteAPI {

    @JsonProperty("telegramUserId")
    private Long telegramUserId;

    @JsonProperty("clothesId")
    private Long clothesId;

}
