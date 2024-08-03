package com.edatatest.RestAPI.controllers;

import com.edatatest.RestAPI.auth.AuthenticationRequest;
import com.edatatest.RestAPI.auth.AuthenticationResponse;
import com.edatatest.RestAPI.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    @MockBean
    private AuthenticationService authService;

    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = mock(AuthenticationService.class);
        loginController = new LoginController(authService);
    }

    @Test
    void testLogin() {

        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("User 1 ");
        request.setPassword("password");

        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setToken("thisIsAToken");
        when(authService.authenticate(any(AuthenticationRequest.class))).thenReturn(authResponse);

        // Act
        ResponseEntity<AuthenticationResponse> response = loginController.login(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authResponse, response.getBody());
    }
}
