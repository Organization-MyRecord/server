package com.mr.myrecord.controller;

import com.mr.myrecord.model.request.PostRequest;
import com.mr.myrecord.model.request.PostUpdateRequest;
import com.mr.myrecord.model.response.PostReadResponse;
import com.mr.myrecord.model.response.PostResponse;
import com.mr.myrecord.model.response.PostUpdateResponse;
import com.mr.myrecord.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create_post")
    public PostResponse create(PostRequest postRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();
        return postService.create(email, postRequest);
    }

    @PutMapping("/update_post")
    public PostUpdateResponse update(PostUpdateRequest postUpdateRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();
        return postService.update(email, postUpdateRequest);
    }

    @GetMapping("/post/{postId}")
    public PostReadResponse read(@PathVariable Long postId) {
        return postService.read(postId);
    }

    @DeleteMapping("/post_delete/{postId}")
    public boolean delete(@PathVariable Long postId) {
        return postService.delete(postId);
    }
}
