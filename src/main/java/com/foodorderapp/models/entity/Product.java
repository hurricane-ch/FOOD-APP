package com.foodorderapp.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String content;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "volume", nullable = false)
    private Integer volume;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "available")
    private Boolean available;
    @Lob
    private byte[] image;
}
