package com.nyrhog.telegramShopping.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteClothesId implements Serializable {

    @Column
    private Long clientId;

    @Column
    private Long clothesId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        FavouriteClothesId that = (FavouriteClothesId) o;
        return Objects.equals(clientId, that.clientId) &&
                Objects.equals(clothesId, that.clothesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clothesId);
    }

}
