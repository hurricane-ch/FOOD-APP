package com.foodorderapp.models.binding.order;

import com.foodorderapp.models.service.ProductServiceModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEditBindingModel {
    private String id;
    private List<ProductServiceModel> products;
    private String userData;
    private String date;
    private BigDecimal sum;
    private String address;
    private Boolean isActive;
}
