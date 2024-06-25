package com.rogerdev.blog_service.domain.permission.dto;

import jakarta.validation.constraints.NotBlank;

public record PermissionReqDTO(
        @NotBlank
        String name
) {
}
