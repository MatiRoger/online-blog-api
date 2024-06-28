package com.rogerdev.blog_service.domain.usersec.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthReqDTO(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
