package com.rogerdev.blog_service.domain.role.dto;

import jakarta.validation.constraints.NotNull;

public record RoleModificationResDTO(
        @NotNull
        Long role_id,
        @NotNull
        Long permisson_id
) {
}
