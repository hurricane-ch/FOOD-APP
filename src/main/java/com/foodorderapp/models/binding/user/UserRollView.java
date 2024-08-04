package com.foodorderapp.models.binding.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRollView {
    private String id;
    private String displayName;
    private String email;
    private List<String> rolesName;
}
