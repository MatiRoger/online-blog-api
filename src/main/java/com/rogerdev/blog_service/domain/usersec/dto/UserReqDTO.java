package com.rogerdev.blog_service.domain.usersec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserReqDTO(
    @NotBlank
    String username,
    @NotBlank
    String password,
    List<Long> roles
) {
}
