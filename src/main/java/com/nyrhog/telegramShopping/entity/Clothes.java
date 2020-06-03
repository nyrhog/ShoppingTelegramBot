package com.nyrhog.telegramShopping.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Clothes {

    //Конструктор
    public Clothes(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Double price;

    /*
    Color: ManyToMany
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "clother_color",
            joinColumns = { @JoinColumn(name = "clother_id") },
            inverseJoinColumns = { @JoinColumn(name = "color_id") })
    private List<Color> colors = new ArrayList<>();

    public void addColor(Color color) {
        colors.add(color);
        color.getClothes().add(this);
    }

    public void removeColor(Color color) {
        colors.remove(color);
        color.getClothes().remove(this);
    }



    /*
    Size: ManyToMany
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "clother_size",
            joinColumns = { @JoinColumn(name = "clother_id") },
            inverseJoinColumns = { @JoinColumn(name = "size_id") })
    private List<Size> sizes = new ArrayList<>();

    public void addSize(Size size) {
        sizes.add(size);
        size.getClothes().add(this);
    }

    public void removeSize(Size size) {
        sizes.remove(size);
        size.getClothes().remove(this);
    }



    /*
    Category: ManyToMany
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "clother_category",
            joinColumns = { @JoinColumn(name = "clother_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") })
    private List<Category> categories = new ArrayList<>();

    public void addCategory(Category category) {
        categories.add(category);
        category.getClothes().add(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.getClothes().remove(this);
    }



    /*
    FavouriteClothes: ManyToMany with extra columns
    */
    @OneToMany(
            mappedBy = "clothes",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FavouriteClothes> favouriteClothes = new ArrayList<>();

    public void addFavouriteClothes(Client client){
        FavouriteClothes favouriteClothes = new FavouriteClothes(client, this);
        this.favouriteClothes.add(favouriteClothes);
//        client.getFavouriteClothes().add(favouriteClothes);
    }

    public void removeFavouriteClothes(Client client) {
        for(FavouriteClothes favouriteClothes : client.getFavouriteClothes()){
            if (favouriteClothes.getClient().equals(client) && favouriteClothes.getClothes().equals(this)) {
//                favouriteClothes.getClient().getFavouriteClothes().remove(favouriteClothes);
                favouriteClothes.setClient(null);
                favouriteClothes.setClothes(null);
            }
        }
    }

    @OneToMany(
            mappedBy = "clothes",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderClothes> orderClothes = new ArrayList<>();

    public void addOrderClothes(OrderClient order, Size size, Color color){
        OrderClothes orderClothes = new OrderClothes(order, this, size.getName(), color.getName());
        this.orderClothes.add(orderClothes);
        order.getOrderClothesList().add(orderClothes);
    }

    public void removeOrderClothes(OrderClient order){
        for (OrderClothes orderClothes : order.getOrderClothesList()){
            if(orderClothes.getClothes().equals(this) && orderClothes.getOrder().equals(order)){
                orderClothes.getClothes().getOrderClothes().remove(orderClothes);
                orderClothes.setClothes(null);
                orderClothes.setOrder(null);
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Objects.equals(id, clothes.id);
    }
}
