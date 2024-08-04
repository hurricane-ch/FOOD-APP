package com.foodorderapp.models.binding.order;

import com.foodorderapp.constants.Errors;
import com.foodorderapp.models.service.ProductServiceModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddBindingModel {
    @NotNull
    private List<ProductServiceModel> products;
    @NotNull
    private String userData;
    @NotNull
    private String date;
    @DecimalMin(value = "5.00", message = Errors.ORDER_SUM_ERROR)
    private BigDecimal sum;
    @NotNull
    private String address;
    @NotNull
    private Boolean isActive;

}
