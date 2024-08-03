package com.edatatest.RestAPI.converters;

import com.edatatest.RestAPI.dto.RoleDto;
import com.edatatest.RestAPI.entities.Role;
import com.edatatest.RestAPI.repositories.RoleRepository;
import org.springframework.stereotype.Component;


@Component
public class RoleConverter {

    private static RoleRepository roleRepository;


    public RoleConverter(RoleRepository roleRepository){
        RoleConverter.roleRepository = roleRepository;
    }


    public static RoleDto convert(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        return roleDto;
    }


    public static Role roleToString(String role) {
        return roleRepository.findByName(role);
    }
}

