package com.foodorderapp.services.interfaces;

import com.foodorderapp.exceptions.UserAlreadyExistAuthenticationException;
import com.foodorderapp.models.binding.auth.SignUpRequestBindingModel;
import com.foodorderapp.models.binding.user.UpdateRoles;
import com.foodorderapp.models.binding.user.UserRollView;
import com.foodorderapp.models.service.UserServiceModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserRollView> getAllUsers();

    UserServiceModel registerNewUser(SignUpRequestBindingModel signUpRequest)
            throws UserAlreadyExistAuthenticationException;
    UserServiceModel updateExistingUser(Long id,String name , String email)
            throws UserAlreadyExistAuthenticationException;

    UserServiceModel findUserByEmail(String email);

    Optional<UserServiceModel> findUserById(Long id);

   void changeRole(UpdateRoles dto);
}
