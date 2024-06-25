package com.rogerdev.blog_service.domain.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RoleReqDTO(
        @NotBlank
        String name,
        List<Long> permissions
) {
}
