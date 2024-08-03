package com.edatatest.RestAPI.converters;

import com.edatatest.RestAPI.dto.UserDto;
import com.edatatest.RestAPI.entities.Role;
import com.edatatest.RestAPI.entities.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter {
    public static User convert(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setRoles(userDto.getRoles().stream()
                .map(RoleConverter::roleToString)
                .collect(Collectors.toSet()));
        return user;
    }

    public static UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        return userDto;
 }
}