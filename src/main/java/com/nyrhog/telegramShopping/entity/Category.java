package com.nyrhog.telegramShopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy="categories")
    private List<Clothes> clothes = new ArrayList<>();

    public void addClothes(Clothes clothes) {
        this.clothes.add(clothes);
        clothes.getCategories().add(this);
    }

    public void removeClothes(Clothes clothes) {
        this.clothes.remove(clothes);
        clothes.getCategories().remove(this);
    }

    public Category(String name){
        this.name = name;
    }

}
