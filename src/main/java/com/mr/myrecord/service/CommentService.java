package com.mr.myrecord.service;

import com.mr.myrecord.model.entity.Comment;
import com.mr.myrecord.model.entity.Post;
import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.CommentRepository;
import com.mr.myrecord.model.repository.PostRepository;
import com.mr.myrecord.model.repository.UserRepository;
import com.mr.myrecord.model.request.CommentRequest;
import com.mr.myrecord.model.request.CommentUpdateRequest;
import com.mr.myrecord.model.response.CommentResponse;
import com.mr.myrecord.model.response.NestedCommentResponse;
import com.mr.myrecord.model.response.PostReadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public CommentResponse createComment(String email, Long postId, CommentRequest commentRequest) {
        User user = userRepository.findByEmail(email);
        Post post = postRepository.findById(postId).orElse(null);
        Comment comment;
        /**
         * 대댓글이 아니면 0L
         */
        if (commentRequest.getParentCommentId() == 0L) {
            comment = Comment.builder()
                    .comment(commentRequest.getComment())
                    .userCommentId(user)
                    .postId(post)
                    .parentCommentId(null)
                    .firstComment(true)
                    .commentTime(LocalDateTime.now())
                    .commentList(new ArrayList<>())
                    .build();
        }
        /**
         * 대댓글일 경우
         */
        else {
            Comment parentComment = commentRepository.findById(commentRequest.getParentCommentId()).orElse(null);
            comment = Comment.builder()
                    .comment(commentRequest.getComment())
                    .userCommentId(user)
                    .postId(post)
                    .parentCommentId(parentComment)
                    .commentTime(LocalDateTime.now())
                    .firstComment(false)
                    .commentList(new ArrayList<>())
                    .build();
        }
        commentRepository.save(comment);

        CommentResponse res = CommentResponse.builder()
                .comment(comment.getComment())
                .userName(user.getName())
                .userImage(user.getImage())
                .commentList(new ArrayList<>())
                .parentCommendId(commentRequest.getParentCommentId())
                .commentTime(LocalDateTime.now())
                .build();

        return res;

    }

    /**
     * 댓글을 수정 할때 user의 id와 댓글 id를 가진 댓글을 찾아 수정
     */
    public CommentResponse updateComment(String email, CommentUpdateRequest commentRequest) throws Exception {
        User user = userRepository.findByEmail(email);
        Comment comment = commentRepository.findByIdAndUserCommentId(commentRequest.getCommentId(), user.getId());
        if (comment == null) {
            throw new Exception("권한이 없습니다.");
        }
        comment.setComment(commentRequest.getComment());
        commentRepository.save(comment);
        CommentResponse res = CommentResponse.builder()
                .comment(comment.getComment())
                .userName(user.getName())
                .userImage(user.getImage())
                .commentList(new ArrayList<>())
                .parentCommendId(comment.getParentCommentId() == null ? null : comment.getParentCommentId().getId())
                .commentTime(LocalDateTime.now())
                .build();
        return res;
    }

    /**
     * 댓글을 삭제 할때 user의 id와 댓글 id를 가진 댓글을 찾아 삭제
     */
    public boolean deleteComment(String email, Long commentId){
        User user = userRepository.findByEmail(email);
        Comment comment = commentRepository.findByIdAndUserCommentId(commentId, user.getId());
        if (comment == null) {
            return false;
        }
        commentRepository.delete(comment);
        return true;
    }

    public List<CommentResponse> read(Long postId) throws Exception {
        Post resource = postRepository.findById(postId).orElse(null);
        List<Comment> commentList = commentRepository.findByHello(postId, true);
        List<CommentResponse> commentResponseList = commentList.stream().map(comment -> commentResponse(comment))
                .collect(Collectors.toList());
        if (resource==null) {
            throw new Exception("없는 게시물 id입니다.");
        }
        return commentResponseList;

    }

    private CommentResponse commentResponse(Comment comment) {
        List<NestedCommentResponse> commentResponseList = comment.getCommentList().stream().map(nestedComment -> nestedCommentResponse(nestedComment))
                .collect(Collectors.toList());
        return CommentResponse.builder()
                .commentId(comment.getId())
                .comment(comment.getComment())
                .commentList(commentResponseList)
                .commentTime(comment.getCommentTime())
                .parentCommendId(comment.getParentCommentId() == null ? null : comment.getParentCommentId().getId())
                .userName(comment.getUserCommentId().getName())
                .userImage(comment.getUserCommentId().getImage())
                .build();
    }
    private NestedCommentResponse nestedCommentResponse(Comment comment) {
        return NestedCommentResponse.builder()
                .commentId(comment.getId())
                .comment(comment.getComment())
                .commentList(comment.getCommentList())
                .commentTime(comment.getCommentTime())
                .parentCommendId(comment.getParentCommentId() == null ? null : comment.getParentCommentId().getId())
                .userName(comment.getUserCommentId().getName())
                .userImage(comment.getUserCommentId().getImage())
                .build();
    }
}
