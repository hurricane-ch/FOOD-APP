package com.foodorderapp.models.binding.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageBindingModel {
    @NotNull
    private String name;
    private String type;
    @NotNull
    private byte[] picByte;
}
