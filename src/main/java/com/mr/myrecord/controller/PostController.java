package com.mr.myrecord.controller;

import com.mr.myrecord.model.Header;
import com.mr.myrecord.model.request.PostRequest;
import com.mr.myrecord.model.request.PostUpdateRequest;
import com.mr.myrecord.model.response.*;
import com.mr.myrecord.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * 게시물 생성
     * 로그인 권한 필요
     */
    @ApiOperation(value = "게시물 생성", notes = "JWT 토큰정보 필수")
    @PostMapping("/create_post")
    public Header<PostResponse> create(@RequestBody PostRequest postRequest) throws Exception {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();
            return Header.OK(postService.create(email, postRequest));
        }
        catch (Exception e) {
            return Header.ERROR("로그인 권한이 필요합니다.");
        }
    }

    /**
     * 게시물 수정
     * 로그인 권한 필요
     */
    @ApiOperation(value = "게시물 수정", notes = "JWT 토큰정보 필수")
    @PutMapping("/update_post")
    public Header<PostUpdateResponse> update(@RequestBody PostUpdateRequest postUpdateRequest) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();
            return Header.OK(postService.update(email, postUpdateRequest));
        }
        catch (Exception e) {
            return Header.ERROR("로그인 권한이 필요합니다.");
        }
    }

    /**
     * 게시물 불러오기
     */
    @ApiOperation(value = "게시물 보기", notes = "게시물 id 필수")
    @GetMapping("/post/{postId}")
    public PostReadResponse read(@PathVariable Long postId) throws Exception {
        return postService.read(postId);
    }

    /**
     * 게시물 삭제
     * 로그인 권한 필요
     */
    @ApiOperation(value = "게시물 삭제", notes = "JWT토큰 & 게시물 id 필수")
    @DeleteMapping("/post_delete/{postId}")
    public Header<Boolean> delete(@PathVariable Long postId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();

            return Header.OK(postService.delete(postId));
        }
        catch (Exception e) {
            return Header.ERROR("로그인 권한이 필요합니다.");
        }
    }

    /**
     * 분야별 게시물 불러오기
     */
    @GetMapping("/post")
    @ApiOperation(value = "분야별 게시물 조회", notes = "field 분야 값 필수")
    public Header<FieldPostResponse> fieldPost(@RequestParam("field") String field,
                                       @PageableDefault(sort = "views", direction = Sort.Direction.DESC, size=10) Pageable pageable) {
        return Header.OK(postService.fieldPost(field, pageable));
    }

    /**
     * 최근 게시물 위아래로 2개씩 가져오기
     */
    @GetMapping("/post/another/{postId}")
    @ApiOperation(value = "게시물 기준 위아래로 2개씩 가져오기", notes = "")
    public Header<AnotherPostResponse> anotherPost(@PathVariable Long postId) {
        return Header.OK(postService.anotherPost(postId));
    }

    /**
     * 개시물 검색 기능
     */
    @GetMapping("/search")
    @ApiOperation(value = "게시물 제목, 게시물 내용에서 검색하기")
    public Header<FieldPostResponse> searchPost(
            @RequestParam String keyword,
            @PageableDefault(sort = "views", direction = Sort.Direction.DESC, size=10) Pageable pageable
                           ) {
        return Header.OK(postService.searchPost(keyword, pageable));
    }
}
