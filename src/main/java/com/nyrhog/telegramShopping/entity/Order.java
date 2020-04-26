package com.nyrhog.telegramShopping.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orderclient")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"client", "clothes"})
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_generator")
    @SequenceGenerator(name="order_generator", sequenceName = "order_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column
    private Date applicationDate;

    @Column
    private Date deliveryDate;

    @Column
    //TODO: прочитать про decimal number
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "order_clothes",
            joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "clothes_id") })
    private List<Clothes> clothes = new ArrayList<>();

    public void addClothes(Clothes clothes) {
        this.clothes.add(clothes);
        clothes.getOrders().add(this);
    }

    public void removeClothes(Clothes clothes) {
        this.clothes.remove(clothes);
        clothes.getOrders().remove(this);
    }
}
