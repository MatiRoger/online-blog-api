package com.rogerdev.blog_service.controller;

import com.rogerdev.blog_service.domain.permission.Permission;
import com.rogerdev.blog_service.domain.permission.PermissionService;
import com.rogerdev.blog_service.domain.permission.dto.PermissionReqDTO;
import com.rogerdev.blog_service.domain.permission.dto.PermissionResDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/permission")
//@PreAuthorize("hasRole('ADMIN')")
@PreAuthorize("permitAll()")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping
    @Transactional
    public ResponseEntity<PermissionResDTO> createPermission (@RequestBody @Valid PermissionReqDTO reqData, UriComponentsBuilder uriComponentsBuilder) {
        Permission newPermission = permissionService.savePermission(new Permission(reqData));
        URI url = uriComponentsBuilder.path("/api/permission/{id}").buildAndExpand(newPermission.getId()).toUri();

        return ResponseEntity.created(url).body(new PermissionResDTO(newPermission));
    }
    @GetMapping
    public ResponseEntity<List<PermissionResDTO>> getPermissions () {
        return ResponseEntity.ok()
                .body(permissionService.getPermissions().stream().map(PermissionResDTO::new).toList());
    }
    @GetMapping("/{permissionId}")
    public ResponseEntity<PermissionResDTO> findPermission (@PathVariable Long permissionId) {
        return ResponseEntity.ok()
                .body(new PermissionResDTO(permissionService.findPermission(permissionId)));
    }
    @DeleteMapping("/{permissionId}")
    @Transactional
    public ResponseEntity deletePermission (@PathVariable Long permissionId) {
        return ResponseEntity.noContent().build();
    }
}
