package com.nyrhog.telegramShopping.entity.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FavouriteDTO extends DTO {

   private boolean isFavouriteAdded;

}
