package com.rogerdev.blog_service.domain.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private IRoleRepository roleRepo;

    public Role saveRole (Role newRole) {
        return roleRepo.save(newRole);
    }
    public List<Role> getRoles () {
        return roleRepo.findAll();
    }
    public Role findRole (Long roleId) {
        return roleRepo.getReferenceById(roleId);
    }
    public void deleteRole (Long roleId) {
        roleRepo.deleteById(roleId);
    }
}
