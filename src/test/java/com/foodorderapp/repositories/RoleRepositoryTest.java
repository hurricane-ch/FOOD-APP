package com.foodorderapp.repositories;

import com.foodorderapp.models.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RoleRepositoryTest {

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByName() {
        String roleName = "ROLE_USER";
        Role role = new Role(roleName);
        role.setRoleId(1L);
        role.setUsers(new HashSet<>());

        when(roleRepository.findByName(roleName)).thenReturn(role);

        Role foundRole = roleRepository.findByName(roleName);

        assertNotNull(foundRole);
        assertEquals(roleName, foundRole.getName());
        assertEquals(1L, foundRole.getRoleId());

        verify(roleRepository, times(1)).findByName(roleName);
    }
}