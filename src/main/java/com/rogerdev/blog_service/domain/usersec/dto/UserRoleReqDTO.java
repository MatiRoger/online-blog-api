package com.rogerdev.blog_service.domain.usersec.dto;

import jakarta.validation.constraints.NotNull;

public record UserRoleReqDTO(
        @NotNull
        Long user_id,
        @NotNull
        Long role_id
) {
}
