package com.rogerdev.blog_service.domain.role.dto;

import com.rogerdev.blog_service.domain.permission.Permission;
import com.rogerdev.blog_service.domain.role.Role;

import java.util.ArrayList;
import java.util.List;

public record RoleResDTO(
        Long id,
        String name,
        List<String> permissions

) {
    public RoleResDTO(Role newRole) {
        this(
                newRole.getId(),
                newRole.getName(),
                newRole.getPermissionsList().stream().map(Permission::getName).toList()
        );
    }
}
