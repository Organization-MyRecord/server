package com.mr.myrecord.controller;

import com.mr.myrecord.model.request.CommentRequest;
import com.mr.myrecord.model.request.CommentUpdateRequest;
import com.mr.myrecord.model.response.CommentResponse;
import com.mr.myrecord.model.response.PostReadResponse;
import com.mr.myrecord.service.CommentService;
import com.mr.myrecord.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 게시물 댓글 불러오기
     */
    @ApiOperation(value = "게시물 보기", notes = "게시물 id 필수")
    @GetMapping("/post/{postId}/comment")
    public List<CommentResponse> read(@PathVariable Long postId) throws Exception {
        return commentService.read(postId);
    }

    /**
     * 게시물에 댓글 작성
     */
    @PostMapping("/comment/{postId}")
    public CommentResponse createComment(@PathVariable Long postId,
                                         @RequestBody CommentRequest commentRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();

        return commentService.createComment(email, postId, commentRequest);
    }

    /**
     * 게시물 댓글 수정
     */
    @PutMapping("/comment/{postId}")
    public CommentResponse updateComment(@PathVariable Long postId,
                                         @RequestBody CommentUpdateRequest commentRequest) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();

        return commentService.updateComment(email, commentRequest);
    }

    @DeleteMapping("/comment/{postId}/{commentId}")
    public boolean deleteComment(@PathVariable Long postId,
                                 @PathVariable Long commentId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getUsername();

        return commentService.deleteComment(email, commentId);
    }


}
