package com.rogerdev.blog_service.domain.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    @Autowired
    private IPermissionRepository permissionRepo;
}
