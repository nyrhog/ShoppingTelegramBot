package com.nyrhog.telegramShopping.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clothes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clothes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clothes_generator")
    @SequenceGenerator(name="clothes_generator", sequenceName = "clothes_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column
    private String name;

    @Column
    private Double price;

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

    @ManyToMany(mappedBy="clothes")
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
        order.getClothes().add(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.getClothes().remove(this);
    }


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

    public Clothes(String name){
        this.name = name;
    }

}
