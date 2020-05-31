package com.nyrhog.telegramShopping.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderClothesId implements Serializable {

    @Column
    private Long orderId;

    @Column
    private Long clothesId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderClothesId that = (OrderClothesId) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(clothesId, that.clothesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, clothesId);
    }
}
