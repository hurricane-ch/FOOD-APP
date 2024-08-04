package com.foodorderapp.models.binding.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {

    private Long id;
    private String displayName;
    private String email;
}
