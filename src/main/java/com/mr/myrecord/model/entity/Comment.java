package com.mr.myrecord.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = { "parentCommentId", "userCommentId", "postId", "commentList"})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    private boolean firstComment;

    private LocalDateTime commentTime;

    @JoinColumn(name = "userCommentId")
    @ManyToOne
    private User userCommentId;

    @JoinColumn(name = "postId")
    @ManyToOne
    private Post postId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentCommentId")
    private List<Comment> commentList = new ArrayList<>();

    @JoinColumn(name = "parentCommentId")
    @ManyToOne
    private Comment parentCommentId;

}
