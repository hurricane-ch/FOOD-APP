package com.foodorderapp.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodorderapp.config.CurrentUser;
import com.foodorderapp.constants.Links;
import com.foodorderapp.models.binding.user.LocalUserBindingModel;
import com.foodorderapp.models.binding.user.UpdateRoles;
import com.foodorderapp.models.binding.user.UserRollView;
import com.foodorderapp.models.entity.Role;
import com.foodorderapp.models.service.UserServiceModel;
import com.foodorderapp.models.view.UserViewModel;
import com.foodorderapp.services.interfaces.UserService;
import com.foodorderapp.util.GeneralUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void givenUsers_whenGetAllUsers_thenReturnAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(new UserRollView()));

        mockMvc.perform(MockMvcRequestBuilders.get(Links.API + Links.USER_ALL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
    }
}
