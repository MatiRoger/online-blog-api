package com.rogerdev.blog_service.domain.post;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.rogerdev.blog_service.domain.post.dto.PostReqDTO;
import com.rogerdev.blog_service.domain.post.dto.PostResDTO;
import com.rogerdev.blog_service.domain.usersec.UserSec;
import com.rogerdev.blog_service.domain.usersec.UserService;
import com.rogerdev.blog_service.infra.utils.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping
    @Transactional
    public ResponseEntity<PostResDTO> createPost (
            @RequestBody @Valid PostReqDTO data,
            @RequestHeader("Authorization") String authorization,
            UriComponentsBuilder uriComponentsBuilder
            ) {
        String token = authorization.substring(7);
        DecodedJWT decodedJWT = jwtUtils.validateToken(token);
        UserSec postAuthor = userService.getUserByUsername(jwtUtils.extractUsername(decodedJWT))
                .orElseThrow(EntityNotFoundException::new);
        Post post = new Post(data);
        post.setAuthor(postAuthor);
        Post newPost = postService.savePost(post);
        URI url = uriComponentsBuilder.path("/api/post/{id}").buildAndExpand(newPost.getId()).toUri();
        return ResponseEntity.created(url)
                .body(new PostResDTO(newPost));
    }
    @GetMapping
    public ResponseEntity<List<PostResDTO>> getPosts () {
        return ResponseEntity.ok()
                .body(postService.getPosts().stream().map(PostResDTO::new).toList());
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostResDTO> findPosts(@PathVariable Long postId) {
        return ResponseEntity.ok()
                .body(new PostResDTO(postService.findPost(postId)));
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost (@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
