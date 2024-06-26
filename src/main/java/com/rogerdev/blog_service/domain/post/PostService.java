package com.rogerdev.blog_service.domain.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private IPostRepository postRepo;

    public Post savePost (Post newPost) {
        return postRepo.save(newPost);
    }
    public List<Post> getPosts () {
        return postRepo.findAll();
    }
    public Post findPost (Long postId) {
        return postRepo.getReferenceById(postId);
    }
    public void deletePost (Long postId) {
        postRepo.deleteById(postId);
    }
}
