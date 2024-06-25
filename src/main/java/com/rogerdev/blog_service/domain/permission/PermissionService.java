package com.rogerdev.blog_service.domain.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private IPermissionRepository permissionRepo;

    public Permission savePermission (Permission newPermission) {
        return permissionRepo.save(newPermission);
    }
    public List<Permission> getPermissions() {
        return permissionRepo.findAll();
    }
    public Permission findPermission(Long permissionId) {
        return permissionRepo.getReferenceById(permissionId);
    }
    public void deletePermission (Long permissionId) {
        permissionRepo.deleteById(permissionId);
    }

}
