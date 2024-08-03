package com.edatatest.RestAPI.services;

import com.edatatest.RestAPI.auth.AuthenticationRequest;
import com.edatatest.RestAPI.auth.AuthenticationResponse;
import com.edatatest.RestAPI.auth.RegisterRequest;
import com.edatatest.RestAPI.entities.Role;
import com.edatatest.RestAPI.entities.User;
import com.edatatest.RestAPI.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse createUser(RegisterRequest request) {
       Set<Role> role = request.isRoleAdmin()?
               Set.of(roleService.findByName("standard"), roleService.findByName("admin")) :
               Set.of(roleService.findByName("standard"));

        User user = new User();
                user.setName(request.getUsername());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setRoles(role);

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByName(request.getUsername())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
