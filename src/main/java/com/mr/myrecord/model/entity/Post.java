package com.mr.myrecord.model.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
@ToString(exclude = {"postId", "userPostId", "directoryId", "favoriteUserId", "commentList"})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postName;

    private LocalDateTime postDate;

    private String classification;

    @Column(length = 100000)
    private String content;

    private String postUserEmail;

    private String postImage;

    private Long views;

    @JoinColumn(name = "userPostId")
    @ManyToOne
    private User userPostId;

    @JoinColumn(name = "directoryId")
    @ManyToOne
    private Directory directoryId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "postId", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @JoinColumn(name = "favoriteUserId")
    @ManyToOne
    private User favoriteUserId;
}
