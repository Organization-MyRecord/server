package com.mr.myrecord.controller;

import com.mr.myrecord.model.request.PostRequest;
import com.mr.myrecord.model.request.PostUpdateRequest;
import com.mr.myrecord.model.response.FieldPostResponse;
import com.mr.myrecord.model.response.PostReadResponse;
import com.mr.myrecord.model.response.PostResponse;
import com.mr.myrecord.model.response.PostUpdateResponse;
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
     */
    @ApiOperation(value = "게시물 생성", notes = "JWT 토큰정보 필수")
    @PostMapping("/create_post")
    public PostResponse create(@RequestBody PostRequest postRequest) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();
        return postService.create(email, postRequest);
    }

    /**
     * 게시물 수정
     */
    @ApiOperation(value = "게시물 수정", notes = "JWT 토큰정보 필수")
    @PutMapping("/update_post")
    public PostUpdateResponse update(@RequestBody PostUpdateRequest postUpdateRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();
        return postService.update(email, postUpdateRequest);
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
     */
    @ApiOperation(value = "게시물 삭제", notes = "JWT토큰 & 게시물 id 필수")
    @DeleteMapping("/post_delete/{postId}")
    public boolean delete(@PathVariable Long postId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();

        return postService.delete(postId);
    }

    /**
     * 분야별 게시물 불러오기
     */
    @GetMapping("/post")
    @ApiOperation(value = "분야별 게시물 조회", notes = "field 분야 값 필수")
    public FieldPostResponse fieldPost(@RequestParam("field") String field,
                                       @PageableDefault(sort = "views", direction = Sort.Direction.DESC, size=10) Pageable pageable) {
        return postService.fieldPost(field, pageable);
    }
}
