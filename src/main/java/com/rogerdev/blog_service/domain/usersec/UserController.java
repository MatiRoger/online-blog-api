package com.rogerdev.blog_service.domain.usersec;

import com.rogerdev.blog_service.domain.role.Role;
import com.rogerdev.blog_service.domain.role.RoleService;
import com.rogerdev.blog_service.domain.usersec.dto.UserReqDTO;
import com.rogerdev.blog_service.domain.usersec.dto.UserResDTO;
import com.rogerdev.blog_service.domain.usersec.dto.UserRoleReqDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.DuplicateFormatFlagsException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")

public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @PostMapping
    @Transactional
    public ResponseEntity<UserResDTO> createUser (@RequestBody @Valid UserReqDTO data, UriComponentsBuilder uriBuilder) {
        Set<Role> roleList = new HashSet<>();
        Role readRole;
        UserSec user = new UserSec(data);
        for (Long roleId : data.roles()) {
            readRole = roleService.findRole(roleId);
            if (!roleList.contains(readRole)) roleList.add(readRole);
        }
        user.setRoles(roleList);
        user.setPassword(userService.encryptPassword(user.getPassword()));
        UserSec newUser = userService.saveUser(user);
        URI url = uriBuilder.path("/api/user/{id}").buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(url).body(new UserResDTO(newUser));
    }
    @GetMapping
    public ResponseEntity<List<UserResDTO>> getUsers () {
        return ResponseEntity.ok()
                .body(userService.getUsers().stream().map(UserResDTO::new)
                        .toList());
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserResDTO> findUser (@PathVariable Long userId) {
        return ResponseEntity.ok()
                .body(new UserResDTO(userService.findUser(userId)));
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser (@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/role/add")
    public ResponseEntity asignRole (@RequestBody @Valid UserRoleReqDTO data) {
        Role role = roleService.findRole(data.role_id());
        UserSec user = userService.findUser(data.user_id());

        if (!user.getRoles().add(role)) throw new RuntimeException("Role already assigned");

        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/role/remove")
    public ResponseEntity assignRole (@RequestBody @Valid UserRoleReqDTO data) {
        Role role = roleService.findRole(data.role_id());
        UserSec user = userService.findUser(data.user_id());

        if (!user.getRoles().remove(role)) throw new RuntimeException("Role not present");

        return ResponseEntity.noContent().build();
    }
}
