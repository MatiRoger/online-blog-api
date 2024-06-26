package com.rogerdev.blog_service.domain.post;

import com.rogerdev.blog_service.domain.like.Like;
import com.rogerdev.blog_service.domain.post.dto.PostReqDTO;
import com.rogerdev.blog_service.domain.usersec.UserSec;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Temporal(TemporalType.DATE)
    private LocalDate postDate = LocalDate.now();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserSec author;
    @ElementCollection
    @CollectionTable(name = "post_likes", joinColumns = @JoinColumn(name = "post_id"))
    private Set<Like> likes = new HashSet<>();

    public Post (PostReqDTO dto) {
        this.title = dto.title();
        this.content = dto.content();
    }
}
