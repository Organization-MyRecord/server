package com.mr.myrecord.controller;

import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.response.UserResponse;
import com.mr.myrecord.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // TODO: user 정보 api 만들기
    /**
     * 유저 정보를 보여주는 api
     */
    @GetMapping("/users/{id}")
    public UserResponse read(@PathVariable Long id) {
        return userService.read(id);
    }
}
