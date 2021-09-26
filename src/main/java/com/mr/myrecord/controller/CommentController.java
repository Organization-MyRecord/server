package com.mr.myrecord.controller;

import com.mr.myrecord.model.Header;
import com.mr.myrecord.model.request.CommentRequest;
import com.mr.myrecord.model.request.CommentUpdateRequest;
import com.mr.myrecord.model.response.CommentResponse;
import com.mr.myrecord.service.CommentService;
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
    public Header<List<CommentResponse>> read(@PathVariable Long postId) throws Exception {
        return Header.OK(commentService.read(postId));
    }

    /**
     * 게시물에 댓글 작성
     * 로그인 권한 필요
     */
    @PostMapping("/comment/{postId}")
    public Header<CommentResponse> createComment(@PathVariable Long postId,
                                         @RequestBody CommentRequest commentRequest) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();


            return Header.OK(commentService.createComment(email, postId, commentRequest));
        }
        catch (Exception e) {
            return Header.ERROR("댓글을 작성하려면 로그인이 필요합니다!");
        }
    }

    /**
     * 게시물 댓글 수정
     * 로그인 권한 필요
     */
    @PutMapping("/comment/{postId}")
    public Header<CommentResponse> updateComment(@PathVariable Long postId,
                                         @RequestBody CommentUpdateRequest commentRequest) throws Exception {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();

            return Header.OK(commentService.updateComment(email, commentRequest));
        }
        catch (Exception e) {
            return Header.ERROR("댓글을 수정하려면 로그인이 필요합니다!");
        }
    }

    /**
     * 게시물 댓글 삭제
     * 로그인 권한 필요
     */
    @DeleteMapping("/comment/{postId}/{commentId}")
    public Header<Boolean> deleteComment(@PathVariable Long postId,
                                 @PathVariable Long commentId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = ((User) auth.getPrincipal()).getUsername();

            return Header.OK(commentService.deleteComment(email, commentId));
        }
        catch (Exception e) {
            return Header.ERROR("댓글을 삭제하려면 로그인이 필요합니다!");
        }
    }


}
