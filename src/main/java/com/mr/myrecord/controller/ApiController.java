package com.mr.myrecord.controller;

import com.mr.myrecord.model.Header;
import com.mr.myrecord.model.response.UserResponse;
import com.mr.myrecord.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final UserService userService;

    @GetMapping("/hello")
    public Header<String> hello() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();
            return Header.OK("Hello MyRecord");
        }catch (Exception e) {
            String s = LocalDateTime.now().toString();
            return Header.ERROR(s);
        }
    }

//    @GetMapping("/test/{name}")
//    public UserResponse test(@PathVariable String name) {
//        return userService.findUser(name);
//    }

}
