package com.rogerdev.blog_service.domain.permission;

import com.rogerdev.blog_service.domain.permission.dto.PermissionReqDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;

    public Permission (PermissionReqDTO data) {
        this.name = data.name();
    }
}
