package com.rogerdev.blog_service.domain.like;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@Getter @Setter
public class Like {
    private Long user_id;
    private LocalDate timestamp;
}
