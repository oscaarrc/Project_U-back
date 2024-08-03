package com.edatatest.RestAPI.controllers;

import com.edatatest.RestAPI.auth.AuthenticationResponse;
import com.edatatest.RestAPI.auth.RegisterRequest;
import com.edatatest.RestAPI.dto.UserDto;
import com.edatatest.RestAPI.services.AuthenticationService;
import com.edatatest.RestAPI.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserController {

    private final UserService userService;
    private final AuthenticationService authService;


    @GetMapping("/getUsers")
    public List<UserDto> listUsers(@RequestParam(name = "sort", required = false) String sort) {
        Sort sortObj = Sort.unsorted();
        if (sort != null && !sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            if (sortParams.length > 1) {
                sortObj = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
            } else {
                sortObj = Sort.by(sortParams[0]);
            }
        }
        return userService.findAll(sortObj);
    }

    @GetMapping("/getById")
    public ResponseEntity<UserDto> getUserById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping("/createUser")
    public ResponseEntity<AuthenticationResponse> createUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.createUser(request));
    }

    @GetMapping("/userAuthorities")
    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("admin")) {
                    return true;
                }
            }
        }
        return false;
    }


}
