package com.rogerdev.blog_service.domain.permission.dto;

import com.rogerdev.blog_service.domain.permission.Permission;

public record PermissionResDTO(
        Long id,
        String name
) {
    public PermissionResDTO(Permission permission) {
        this (
                permission.getId(),
                permission.getName()
        );
    }
}
