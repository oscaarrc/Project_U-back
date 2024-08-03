package com.edatatest.RestAPI.converters;

import com.edatatest.RestAPI.dto.RoleDto;
import com.edatatest.RestAPI.entities.Role;
import com.edatatest.RestAPI.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoleConverterTest {

    @Mock
    private RoleRepository roleRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleRepository = mock(RoleRepository.class);
        new RoleConverter(roleRepository);
    }

    @Test
    void convert() {

        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("admin");

        // Act
        RoleDto roleDto = RoleConverter.convert(role);

        // Assert
        assertEquals(role.getId(), roleDto.getId());
        assertEquals(role.getName(), roleDto.getName());
    }

    @Test
    void roleToString() {

        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("admin");
        when(roleRepository.findByName(anyString())).thenReturn(role);

        // Act
        Role result = RoleConverter.roleToString("admin");

        // Assert
        assertEquals(role.getId(), result.getId());
        assertEquals(role.getName(), result.getName());
    }
}
