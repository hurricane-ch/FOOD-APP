package com.foodorderapp.repositories;

import com.foodorderapp.models.entity.User;
import com.foodorderapp.services.impl.BaseServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest extends BaseServiceTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setEnabled(true);
        user.setDisplayName("Test User");
        user.setCreatedDate(new Date());
        user.setModifiedDate(new Date());
        user.setPassword("password");
        user.setRoles(new HashSet<>());

        when(userRepository.findByEmail(email)).thenReturn(user);

        User foundUser = userRepository.findByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        assertTrue(foundUser.isEnabled());
        assertEquals("Test User", foundUser.getDisplayName());

        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void existsByEmail() {
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean exists = userRepository.existsByEmail(email);

        assertTrue(exists);

        verify(userRepository, times(1)).existsByEmail(email);
    }
}