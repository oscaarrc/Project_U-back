package com.edatatest.RestAPI.config;

import com.edatatest.RestAPI.entities.Role;
import com.edatatest.RestAPI.entities.User;
import com.edatatest.RestAPI.services.RoleService;
import com.edatatest.RestAPI.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Role standardRole = new Role();
        standardRole.setName("standard");
        roleService.save(standardRole);

        Role adminRole = new Role();
        adminRole.setName("admin");
        roleService.save(adminRole);

        Set<Role> roles = Set.of(adminRole, standardRole);

        var user = new User();
                user.setName("User 1");
                user.setPassword(passwordEncoder.encode("password"));
                user.setRoles(roles);

        userService.save(user);
    }
}
