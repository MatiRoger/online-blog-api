package com.rogerdev.blog_service.domain.usersec.dto;

import com.rogerdev.blog_service.domain.role.Role;
import com.rogerdev.blog_service.domain.usersec.UserSec;

import java.util.List;

public record UserResDTO(
        String username,
        List<String> roles
) {
    public UserResDTO(UserSec newUser) {
        this(
                newUser.getUsername(),
                newUser.getRoles().stream().map(Role::getName).toList()
        );
    }
}
