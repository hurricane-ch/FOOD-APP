package com.foodorderapp.web.controllers;

import com.foodorderapp.config.CurrentUser;
import com.foodorderapp.constants.Links;
import com.foodorderapp.models.binding.user.LocalUserBindingModel;
import com.foodorderapp.models.binding.user.UpdateRoles;
import com.foodorderapp.models.entity.Role;
import com.foodorderapp.models.view.UserViewModel;
import com.foodorderapp.services.interfaces.UserService;
import com.foodorderapp.util.GeneralUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Links.API)
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(
            UserService userService,
            ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = Links.USER_ALL)
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping(path = Links.USER_ROLE)
    public ResponseEntity<?> changeRole(@RequestBody UpdateRoles dto) {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(path = Links.USER_CURRENT)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUserBindingModel user) {
        var userViewModel = this.modelMapper.map(this.userService.findUserByEmail(user.getUser().getEmail()), UserViewModel.class);
        return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
    }
}