package com.mr.myrecord.controller;

import com.mr.myrecord.exception.UnAuthorizationException;
import com.mr.myrecord.model.request.UserLoginRequest;
import com.mr.myrecord.model.response.UserResponse;
import com.mr.myrecord.security.entity.JwtUtil;
import com.mr.myrecord.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    // TODO: user 정보 api 만들기
    /**
     * 유저 정보를 보여주는 api
     */
    @GetMapping("/")
    public String welcome() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return ((User) auth.getPrincipal()).getUsername();
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody UserLoginRequest userLoginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword())
            );
        }catch (Exception e) {
            throw new Exception("invaild username/password");
        }
        return jwtUtil.generateToken(userLoginRequest.getEmail());
    }
}
