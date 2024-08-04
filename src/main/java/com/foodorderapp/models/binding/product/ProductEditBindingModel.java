package com.foodorderapp.models.binding.product;

import com.foodorderapp.constants.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEditBindingModel {

    private String id;
    @Length(min = 3, message = Errors.NAME_ERROR)
    private String name;
    @Length(min = 3, message = Errors.DESCRIPTION_ERROR)
    private String content;
    @Length(min = 4, message = Errors.PRODUCT_TYPE_ERROR)
    private String type;
    @Min(value = 10, message = Errors.VOLUME_ERROR)
    private Integer volume;
    @DecimalMin(value = "0.01", message = Errors.PRICE_ERROR)
    private double price;
    private byte[] image;
}
