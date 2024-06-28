package com.rogerdev.blog_service.domain.usersec.dto;

public record AuthResDTO(
    String message,
    String user,
    String token
) {
}
