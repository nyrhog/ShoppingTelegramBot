package com.nyrhog.telegramShopping.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class OrderClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderClothes> orderClothesList = new ArrayList<>();

    public void addOrderClothes(Clothes clothes, Size size, Color color){
        OrderClothes orderClothes = new OrderClothes(this, clothes, size.getName(), color.getName());
        this.orderClothesList.add(orderClothes);
        clothes.getOrderClothes().add(orderClothes);
    }

    public void removeOrderClothes(Clothes clothes){
        for (OrderClothes orderClothes : clothes.getOrderClothes()){
            if(orderClothes.getOrder().equals(this) && orderClothes.getClothes().equals(clothes)){
                orderClothes.getClothes().getOrderClothes().remove(orderClothes);
                orderClothes.setClothes(null);
                orderClothes.setOrder(null);
            }
        }
    }


}
