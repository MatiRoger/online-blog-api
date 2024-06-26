package com.rogerdev.blog_service.domain.post.dto;

import com.rogerdev.blog_service.domain.like.Like;
import com.rogerdev.blog_service.domain.post.Post;

import java.time.LocalDate;
import java.util.Set;

public record PostResDTO(
        String title,
        String content,
        String author,
        LocalDate postDate,
        Set<Like> likes

) {
    public PostResDTO(Post newPost) {
        this(
                newPost.getTitle(),
                newPost.getContent(),
                newPost.getAuthor().getUsername(),
                newPost.getPostDate(),
                newPost.getLikes()
        );
    }
}
