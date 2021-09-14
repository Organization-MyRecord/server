package com.mr.myrecord.model.repository;

import com.mr.myrecord.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select u from Comment u where u.postId.id = ?1 and u.parentCommentId = ?2")
    List<Comment> findByPostIdAndParentCommentId(Long id, Comment comment);

    @Query("select u from Comment u where u.postId.id = ?1 and u.firstComment = ?2")
    List<Comment> findByHello(Long id, boolean tf);

    @Query("select u from Comment u where u.id = ?1 and u.userCommentId.id=?2")
    Comment findByIdAndUserCommentId(Long id, Long userId);
}