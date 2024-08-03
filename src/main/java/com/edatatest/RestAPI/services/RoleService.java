package com.edatatest.RestAPI.services;

import com.edatatest.RestAPI.entities.Role;
import com.edatatest.RestAPI.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name){return roleRepository.findByName(name);}

    public void save(Role role) {roleRepository.save(role);}
}
