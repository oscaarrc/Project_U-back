package com.edatatest.RestAPI.controllers;

import com.edatatest.RestAPI.auth.AuthenticationResponse;
import com.edatatest.RestAPI.auth.RegisterRequest;
import com.edatatest.RestAPI.dto.UserDto;
import com.edatatest.RestAPI.entities.Role;
import com.edatatest.RestAPI.entities.User;
import com.edatatest.RestAPI.services.AuthenticationService;
import com.edatatest.RestAPI.services.JwtService;
import com.edatatest.RestAPI.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationService authService;

    private UserController userController;


    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        userService = mock(UserService.class);
        authService = mock(AuthenticationService.class);
        userController = new UserController(userService, authService);
    }

    @Test
    void listUsers_WithoutSort() {

        //Arrange
        List<UserDto> users = new ArrayList<>();

        UserDto user1 = new UserDto();
        user1.setId(1L);
        user1.setName("User 1");
        user1.setRoles(Set.of("admin", "standard"));

        UserDto user2 = new UserDto();
        user2.setId(2L);
        user2.setName("User 2");
        user2.setRoles(Set.of("standard"));

        users.add(user1);
        users.add(user2);
        when(userService.findAll(any())).thenReturn(users);

        //Act
        List<UserDto> result = userController.listUsers(null);

        //Assert
        assertEquals(2, result.size());
        verify(userService).findAll(any());
    }

    @Test
    void getUserById() {

        //Arrange
        UserDto user = new UserDto();
        user.setId(1L);
        user.setName("User 1");
        when(userService.findUserById(1L)).thenReturn(user);

        //Act
        ResponseEntity<UserDto> response = userController.getUserById(1L);

        // Assert
        assertEquals(user, response.getBody());
        verify(userService).findUserById(1L);
    }

    @Test
    void createUser() {

        // Arrange
        jwtService = mock(JwtService.class);

        Role role = new Role();
        role.setName("admin");

        User user1 = new User();
        user1.setName("User 1");
        user1.setPassword("password");
        user1.setRoles(Set.of(role));

        RegisterRequest registerRequest = new RegisterRequest(
                user1.getUsername(),
                user1.getPassword(),
                true);

        when(jwtService.generateToken(user1)).thenReturn("thisIsAToken");
        AuthenticationResponse authResponse = new AuthenticationResponse(jwtService.generateToken(user1),
                null);
        when(authService.createUser(registerRequest)).thenReturn(authResponse);

        // Act
        ResponseEntity<AuthenticationResponse> response = userController.createUser(registerRequest);

        // Assert
        assertEquals(authResponse, response.getBody());
        verify(authService).createUser(any());
    }
}
