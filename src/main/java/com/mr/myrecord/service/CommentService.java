package com.mr.myrecord.service;

import com.mr.myrecord.model.entity.Comment;
import com.mr.myrecord.model.entity.Post;
import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.CommentRepository;
import com.mr.myrecord.model.repository.PostRepository;
import com.mr.myrecord.model.repository.UserRepository;
import com.mr.myrecord.model.request.CommentRequest;
import com.mr.myrecord.model.response.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
                    .commentList(new ArrayList<>())
                    .build();
        }
        commentRepository.save(comment);

        CommentResponse res = CommentResponse.builder()
                .comment(comment.getComment())
                .userEmail(email)
                .userImage(user.getImage())
                .postId(postId)
                .commentList(new ArrayList<>())
                .parentCommendId(commentRequest.getParentCommentId())
                .build();

        return res;

    }
}
