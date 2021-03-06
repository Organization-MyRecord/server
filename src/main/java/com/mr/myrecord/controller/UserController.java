package com.mr.myrecord.controller;

import com.mr.myrecord.model.Header;
import com.mr.myrecord.model.request.ChangePw;
import com.mr.myrecord.model.request.CheckPw;
import com.mr.myrecord.model.request.UserLoginRequest;
import com.mr.myrecord.model.request.UserUpdateRequest;
import com.mr.myrecord.model.response.LoginResponse;
import com.mr.myrecord.model.response.PageResponse;
import com.mr.myrecord.model.response.UserUpdateResponse;
import com.mr.myrecord.security.entity.JwtUtil;
import com.mr.myrecord.service.UserService;
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
            @PageableDefault(sort = "postDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        return userService.read(email, pageable);
    }

    @ApiOperation(value = "개인 정보 수정", notes = "개인 정보 수정 JWT 토큰 필요")
    @PutMapping("/mypage")
    public Header<UserUpdateResponse> update(UserUpdateRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();

            return Header.OK(userService.update(email, request));
        } catch (Exception e) {
            return Header.ERROR("로그인 권한이 필요합니다.");
        }
    }

    @ApiOperation(value = "로그인 페이지", notes = "JWT 토큰을 전달")
    @PostMapping("/authenticate")
    public LoginResponse generateToken(
            @ApiParam(value = "!!이메일 주소, 비밀번호 필수!!", required = true, example = "email:test@naver.com, password:****")
            @RequestBody UserLoginRequest userLoginRequest
    ) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword())
            );
            return userService.login(jwtUtil.generateToken(userLoginRequest.getEmail()), userLoginRequest.getEmail());
        } catch (Exception e) {
            return LoginResponse.builder()
                    .isOk(false)
                    .description("잘못된 아이디 혹은 비밀번호입니다.")
                    .build();
        }
    }

    @ApiOperation(value = "비밀번호 변경 전 비밀번호 확인 페이지", notes = "기존 비밀번호 입력 필요")
    @PostMapping("/CheckPw")
    public Header<String> checkPw(@RequestBody CheckPw request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();
            if (userService.checkPw(email, request)==true) {
                return Header.OK("계정정보를 확인했습니다.");
            }
            else
                return Header.ERROR("비밀번호가 올바르지 않습니다.");
        } catch (Exception e) {
            return Header.ERROR("로그인이 필요합니다.");
        }
    }


    @PutMapping("/ChangePasspw")
    @ApiOperation(value = "비밀번호 변경 페이지", notes = "새로운 비밀번호 입력 필요")
    public Header<String> changePw(@RequestBody ChangePw request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();

            return Header.OK(userService.changePw(email, request));
        } catch (Exception e) {
            return Header.ERROR("로그인이 필요합니다.");
        }
    }
}
