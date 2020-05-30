package com.nyrhog.telegramShopping.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class FavouriteClothes {


    public FavouriteClothes(Client client, Clothes clothes, String size, String color) {
        this.client = client;
        this.clothes = clothes;
        this.size = size;
        this.color = color;
        this.id = new FavouriteClothesId(client.getId(), clothes.getId());
    }

    @EmbeddedId
    private FavouriteClothesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clientId")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clothesId")
    private Clothes clothes;
    
    @Column
    private String size;
    
    private String color;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        FavouriteClothes that = (FavouriteClothes) o;
        return Objects.equals(client, that.client) &&
                Objects.equals(clothes, that.clothes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, clothes);
    }

}
