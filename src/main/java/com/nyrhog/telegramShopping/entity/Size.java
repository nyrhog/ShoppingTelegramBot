package com.nyrhog.telegramShopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "size")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "size_generator")
    @SequenceGenerator(name = "size_generator", sequenceName = "size_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "sizes")
    private List<Clothes> clothes = new ArrayList<>();

    public void addClothes(Clothes clothes) {
        this.clothes.add(clothes);
        clothes.getSizes().add(this);
    }

    public void removeClothes(Clothes clothes) {
        this.clothes.remove(clothes);
        clothes.getSizes().remove(this);
    }

    public Size(String name){
        this.name = name;
    }

}
