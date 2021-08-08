package com.mr.myrecord.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(exclude = {"parentCommentId", "parent_comment_id", "user_comment_id", "post_id"})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @JoinColumn(name = "user_comment_id")
    @ManyToOne
    private User userCommentId;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post postId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentCommentId")
    private List<Comment> commentList = new ArrayList<>();

    @JoinColumn(name = "parent_comment_id")
    @ManyToOne
    private Comment parentCommentId;
}
