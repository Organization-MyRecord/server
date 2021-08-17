package com.mr.myrecord.model.entity;

import lombok.*;

import java.util.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(exclude = {"postId", "user_post_id", "directory_id", "favorite_user_id"})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postName;

    private LocalDateTime postDate;

    private String classification;

    private String content;

    private String postImage;

    private Long views;

    @JoinColumn(name = "user_post_id")
    @ManyToOne
    private User userPostId;

    @JoinColumn(name = "directory_id")
    @ManyToOne
    private Directory directoryId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "postId")
    private List<Comment> commentList = new ArrayList<>();

    @JoinColumn(name = "favorite_user_id")
    @ManyToOne
    private User favoriteUserId;
}