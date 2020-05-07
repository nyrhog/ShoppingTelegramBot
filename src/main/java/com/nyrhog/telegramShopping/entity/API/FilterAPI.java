package com.nyrhog.telegramShopping.entity.API;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FilterAPI {

    @JsonProperty("color")
    List<String> colors;

    @JsonProperty("size")
    List<String> sizes;

}
