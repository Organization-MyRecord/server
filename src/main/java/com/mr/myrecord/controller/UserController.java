package com.mr.myrecord.controller;

import com.mr.myrecord.exception.UnAuthorizationException;
import com.mr.myrecord.model.request.UserLoginRequest;
import com.mr.myrecord.model.response.UserResponse;
import com.mr.myrecord.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // TODO: user 정보 api 만들기
    /**
     * 유저 정보를 보여주는 api
     */
    @GetMapping("/users")
    public UserResponse read(
    ) {
        /**
         * Authentication은 Authorization: Bearer <token> 형태!
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = (Claims) authentication.getPrincipal();
        if(claims == null) {
            throw new UnAuthorizationException("인가되지 않은 사용자");
        }
        String email = claims.get("email", String.class);
        return userService.read(email);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest user) {
        return userService.login(user.getEmail(), user.getPassword());
    }
}
