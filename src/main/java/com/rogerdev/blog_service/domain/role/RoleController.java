package com.rogerdev.blog_service.domain.role;

import com.rogerdev.blog_service.domain.permission.Permission;
import com.rogerdev.blog_service.domain.permission.PermissionService;
import com.rogerdev.blog_service.domain.role.dto.RoleModificationResDTO;
import com.rogerdev.blog_service.domain.role.dto.RoleReqDTO;
import com.rogerdev.blog_service.domain.role.dto.RoleResDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/role")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @PostMapping
    @Transactional
    public ResponseEntity<RoleResDTO> createRole (@RequestBody @Valid RoleReqDTO data, UriComponentsBuilder uriComponentsBuilder) {
        Set<Permission> permissions = new HashSet<>();
        Role role = new Role(data);
        for (Long id : data.permissions()) {
            permissions.add(permissionService.findPermission(id));
        }
        if (!permissions.isEmpty()) role.setPermissionsList(permissions);
        Role newRole = roleService.saveRole(role);
        URI url = uriComponentsBuilder.path("/api/role/{id}").buildAndExpand(newRole.getId()).toUri();
        return ResponseEntity.created(url).body(new RoleResDTO(newRole));
    }
    @GetMapping
    public ResponseEntity<List<RoleResDTO>> getRoles () {
        return ResponseEntity.ok()
                .body(roleService.getRoles().stream().map(RoleResDTO::new).toList());
    }
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResDTO> findRole (@PathVariable Long roleId) {
        return ResponseEntity.ok().body(new RoleResDTO(roleService.findRole(roleId)));
    }
    @DeleteMapping("/{roleId}")
    @Transactional
    public ResponseEntity deleteRole (@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/permission/remove")
    @Transactional
    public ResponseEntity<RoleResDTO> removePermission (@RequestBody RoleModificationResDTO modificationData) {
        Role role = roleService.findRole(modificationData.role_id());
        role.getPermissionsList().remove(permissionService.findPermission(modificationData.permisson_id()));
        return ResponseEntity.ok().body(new RoleResDTO(roleService.saveRole(role)));
    }
    @PatchMapping("/permission/add")
    @Transactional
    public ResponseEntity<RoleResDTO> addPermission (@RequestBody RoleModificationResDTO modificationData) {
        Role role = roleService.findRole(modificationData.role_id());
        role.getPermissionsList().add(permissionService.findPermission(modificationData.permisson_id()));
        return ResponseEntity.ok().body(new RoleResDTO(roleService.saveRole(role)));
    }
}
