package com.rogerdev.blog_service.domain.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private IRoleRepository roleRepo;
}
