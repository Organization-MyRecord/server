package com.mr.myrecord.controller;

import com.mr.myrecord.model.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ApiController {

    @GetMapping("/hello")
    public String hello() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();
            return "Hello MyRecord";
        }catch (Exception e) {
            String s
                    = LocalDateTime.now().toString();
            return s;
        }
    }

}
