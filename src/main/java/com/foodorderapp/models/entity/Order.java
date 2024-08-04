package com.foodorderapp.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "userData", nullable = false)
    private String userData;
    @Column(name = "date", nullable = false)
    private String date;
    @Column(name = "sum", nullable = false)
    private BigDecimal sum;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @ManyToMany(targetEntity = Product.class,fetch = FetchType.EAGER)
    @JoinTable(name = "orders_products",
            joinColumns = { @JoinColumn(name = "order_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "id") }
    )
    private List<Product> products = new ArrayList<>();

}
