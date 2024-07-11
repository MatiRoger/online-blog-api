package com.rogerdev.blog_service.controller;

import com.rogerdev.blog_service.domain.usersec.UserDetailsServiceImp;
import com.rogerdev.blog_service.domain.usersec.dto.AuthReqDTO;
import com.rogerdev.blog_service.domain.usersec.dto.AuthResDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserDetailsServiceImp userDetailsService;
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResDTO> login (@RequestBody @Valid AuthReqDTO loginRequest) {
        return ResponseEntity.ok().body(userDetailsService.login(loginRequest));
    }
}
