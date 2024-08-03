package com.edatatest.RestAPI.repositories;

import com.edatatest.RestAPI.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

