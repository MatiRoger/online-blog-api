package com.rogerdev.blog_service.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PostReqDTO(
        @NotBlank
        String title,
        @NotBlank
        String content,
        @NotNull
        Long author_id
) {
}
