package com.rogerdev.blog_service.domain.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private IPostRepository postRepo;
}
