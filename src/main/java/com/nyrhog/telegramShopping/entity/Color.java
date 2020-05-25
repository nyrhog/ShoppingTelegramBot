package com.nyrhog.telegramShopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "color")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy="colors")
    private List<Clothes> clothes = new ArrayList<>();

    public void addClothes(Clothes clothes) {
        this.clothes.add(clothes);
        clothes.getColors().add(this);
    }

    public void removeClothes(Clothes clothes) {
        this.clothes.remove(clothes);
        clothes.getColors().remove(this);
    }

    public Color(String name){
        this.name = name;
    }
}
