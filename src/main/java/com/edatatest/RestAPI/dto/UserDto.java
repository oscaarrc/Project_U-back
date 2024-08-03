package com.edatatest.RestAPI.dto;

import lombok.ToString;

import java.util.Set;

@ToString
public class UserDto {

    private Long id;
    private String name;
    private Set<String> roles;


    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public Set<String> getRoles() {return roles;}

    public void setRoles(Set<String> roles) {this.roles = roles;}


}

