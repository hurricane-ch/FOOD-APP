package com.foodorderapp.services.impl;

import com.foodorderapp.exceptions.ResourceNotFoundException;
import com.foodorderapp.repositories.UserRepository;
import com.foodorderapp.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocalUserDetailServiceImplTest extends BaseServiceTest {

    private UserService userService;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private LocalUserDetailServiceImpl localUserDetailService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        modelMapper = new ModelMapper();
        userRepository = mock(UserRepository.class);
        localUserDetailService = new LocalUserDetailServiceImpl(userService, modelMapper, userRepository);
    }

    @Test
    void loadUserByUsername_userFound() {

    }

    @Test
    void loadUserByUsername_userNotFound() {
        when(userService.findUserByEmail("test@example.com")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            localUserDetailService.loadUserByUsername("test@example.com");
        });
    }

    @Test
    void loadUserById_userFound() {
    }

    @Test
    void loadUserById_userNotFound() {
        when(userService.findUserById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            localUserDetailService.loadUserById(1L);
        });
    }
}