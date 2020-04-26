package com.nyrhog.telegramShopping.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_generator")
    @SequenceGenerator(name="client_generator", sequenceName = "client_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column
    private String name;

    @Column
    private Long telegramUserID;

    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Order> orders = new ArrayList<>();


    /*
    As you see we need to do something like "recursion" below
     */
    public void addOrder(Order order) {
        orders.add(order);
        order.setClient(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setClient(null);
    }
}

