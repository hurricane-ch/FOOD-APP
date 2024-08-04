package com.foodorderapp.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "picByte", length = 100000, nullable = false)
    private byte[] picByte;
}

