package com.rogerdev.blog_service.domain.like;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter @Setter
public class Like {
    private String user_name;
    private LocalDate timestamp = LocalDate.now();

    public Like(String user_name) {
        this.user_name = user_name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return Objects.equals(user_name, like.user_name);
    }
}
