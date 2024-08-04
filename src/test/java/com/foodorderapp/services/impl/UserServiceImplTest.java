package com.foodorderapp.services.impl;

import com.foodorderapp.exceptions.UserAlreadyExistAuthenticationException;
import com.foodorderapp.models.binding.auth.SignUpRequestBindingModel;
import com.foodorderapp.models.entity.Role;
import com.foodorderapp.models.entity.User;
import com.foodorderapp.models.service.UserServiceModel;
import com.foodorderapp.repositories.RoleRepository;
import com.foodorderapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest extends BaseServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private SignUpRequestBindingModel signUpRequest;
    private UserServiceModel userServiceModel;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRoles(Set.of(new Role(Role.ROLE_USER)));

        signUpRequest = new SignUpRequestBindingModel();
        signUpRequest.setEmail("test@test.com");
        signUpRequest.setPassword("password");

        userServiceModel = new UserServiceModel();
        userServiceModel.setId(1L);
        userServiceModel.setEmail("test@test.com");
        userServiceModel.setPassword("password");
    }

    @Test
    void registerNewUser_Success() throws UserAlreadyExistAuthenticationException {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserServiceModel.class))).thenReturn(userServiceModel);
        when(roleRepository.findByName(Role.ROLE_USER)).thenReturn(new Role(Role.ROLE_USER));

        UserServiceModel result = userService.registerNewUser(signUpRequest);

        assertEquals(userServiceModel.getId(), result.getId());
        assertEquals(userServiceModel.getEmail(), result.getEmail());
        assertEquals(userServiceModel.getPassword(), result.getPassword());

        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(roleRepository, times(1)).findByName(Role.ROLE_USER);
    }

    @Test
    void registerNewUser_UserAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        assertThrows(UserAlreadyExistAuthenticationException.class, () -> {
            userService.registerNewUser(signUpRequest);
        });
        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, never()).save(any(User.class)); // Ensure save is never called
    }

    @Test
    void findUserByEmail_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserServiceModel.class))).thenReturn(userServiceModel);

        UserServiceModel result = userService.findUserByEmail("test@test.com");

        assertEquals(userServiceModel.getId(), result.getId());
        assertEquals(userServiceModel.getEmail(), result.getEmail());
        assertEquals(userServiceModel.getPassword(), result.getPassword());

        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void findUserById_Success() {

    }

    @Test
    void findUserById_UserNotFound() {

    }

    @Test
    void updateExistingUser_Success() {
    }
}