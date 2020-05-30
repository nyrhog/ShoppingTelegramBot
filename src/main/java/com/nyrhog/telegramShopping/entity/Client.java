package com.nyrhog.telegramShopping.entity;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @NaturalId
    private Long telegramUserID;



    /*
    FavouriteClothes: ManyToMany with extra columns
     */
    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FavouriteClothes> favouriteClothes = new ArrayList<>();

    public void addFavouriteClothes(Clothes clothes, Size size, Color color) {
        FavouriteClothes favouriteClothes = new FavouriteClothes(this, clothes, size.getName(), color.getName());
        this.favouriteClothes.add(favouriteClothes);
        clothes.getFavouriteClothes().add(favouriteClothes);
    }

    public void removeFavouriteClothes(Clothes clothes) {
        for(FavouriteClothes favouriteClothes : clothes.getFavouriteClothes()){
            if (favouriteClothes.getClient().equals(this) && favouriteClothes.getClothes().equals(clothes)) {
                favouriteClothes.getClothes().getFavouriteClothes().remove(favouriteClothes);
                favouriteClothes.setClient(null);
                favouriteClothes.setClothes(null);
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(telegramUserID, client.telegramUserID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramUserID);
    }
}

