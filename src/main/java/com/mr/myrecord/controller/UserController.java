package com.mr.myrecord.controller;

import com.mr.myrecord.exception.UnAuthorizationException;
import com.mr.myrecord.model.request.UserLoginRequest;
import com.mr.myrecord.model.request.UserUpdateRequest;
import com.mr.myrecord.model.response.LoginResponse;
import com.mr.myrecord.model.response.PageResponse;
import com.mr.myrecord.model.response.UserResponse;
import com.mr.myrecord.model.response.UserUpdateResponse;
import com.mr.myrecord.security.entity.JwtUtil;
import com.mr.myrecord.service.UserService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 유저 정보를 보여주는 api
     */
    @GetMapping("/")
    public String welcome() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return ((User) auth.getPrincipal()).getUsername();
    }

    @ApiOperation(value = "마이 페이지 and 다른 유저 페이지", notes = "JWT 토큰 인증하고 마이 페이지 API 반환")
    @GetMapping("/mypage")
    public PageResponse mypage(
            @RequestParam String email,
            @PageableDefault(sort = "postDate", direction = Sort.Direction.DESC, size=10)Pageable pageable) {
        return userService.read(email, pageable);
    }

    @ApiOperation(value = "개인 정보 수정", notes = "개인 정보 수정 JWT 토큰 필요")
    @PutMapping("/mypage")
    public UserUpdateResponse update(UserUpdateRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();

        return userService.update(email, request);
    }

    @ApiOperation(value = "로그인 페이지", notes = "JWT 토큰을 전달")
    @PostMapping("/authenticate")
    public LoginResponse generateToken(
            @ApiParam(value = "!!이메일 주소, 비밀번호 필수!!", required = true, example = "email:test@naver.com, password:****")
            @RequestBody UserLoginRequest userLoginRequest
    ) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword())
            );
        }catch (Exception e) {
            throw new Exception("invaild username/password");
        }
        return userService.login(jwtUtil.generateToken(userLoginRequest.getEmail()), userLoginRequest.getEmail());
    }
}
