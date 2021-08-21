package com.mr.myrecord.controller;

import com.mr.myrecord.model.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/hello")
    public String hello() {
        return "Hello MyRecord";
    }

    @GetMapping("/hell")
    public Long hell() {
        return postRepository.findPostNum(4L);
    }

}
