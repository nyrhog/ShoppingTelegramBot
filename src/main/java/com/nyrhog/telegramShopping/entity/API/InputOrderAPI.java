package com.nyrhog.telegramShopping.entity.API;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.SecondaryTable;

@Data
public class InputOrderAPI {

    @JsonProperty("telegramUser_id")
    Long telegramUserId;

    @JsonProperty("clothes_id")
    Long clothesId;

    @JsonProperty("color")
    String color;

    @JsonProperty("size")
    String size;

    @JsonProperty("count")
    String count;
}
