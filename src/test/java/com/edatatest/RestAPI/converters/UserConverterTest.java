package com.edatatest.RestAPI.converters;

import com.edatatest.RestAPI.dto.UserDto;
import com.edatatest.RestAPI.entities.Role;
import com.edatatest.RestAPI.entities.User;
import com.edatatest.RestAPI.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserConverterTest {

    @Mock
    private RoleConverter roleConverter;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleRepository = mock(RoleRepository.class);
        roleConverter = new RoleConverter(roleRepository);
    }

    @Test
    void convertUserDtoToUser() {

        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("admin");

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("testuser");
        userDto.setRoles(Set.of("admin"));
        when(RoleConverter.roleToString("admin")).thenReturn(role);

        // Act
        User user = UserConverter.convert(userDto);

        // Assert
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getName(), user.getName());
        assertEquals(1, user.getRoles().size());
        assertEquals("admin", user.getRoles().iterator().next().getName());
    }

    @Test
    void convertUserToUserDto() {

        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("admin");

        User user = new User();
        user.setId(1L);
        user.setName("testuser");
        user.setRoles(Set.of(role));


        // Act
        UserDto userDto = UserConverter.convert(user);

        // Assert
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(1, userDto.getRoles().size());
        assertEquals("admin", userDto.getRoles().iterator().next());
    }
}
