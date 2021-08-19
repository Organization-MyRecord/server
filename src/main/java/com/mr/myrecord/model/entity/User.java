package com.mr.myrecord.model.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(exclude = {"userDirectoryId", "userCommentId", "userPostId", "favoriteUserId", "favoriteUserId", "favorite_user_id" })
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String image;

    private String name;

    private String major;

    private String detailMajor;

    private String field;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private String birth;

    private int age;

    private String content;

    private String email;

    private int reporterCount = 3;

    private int reportedCount = 3;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userDirectoryId")
    private List<Directory> directoryList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userCommentId")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userPostId")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "favoriteUserId")
    private List<User> favoriteUserList = new ArrayList<>();

    @JoinColumn(name = "favorite_user_id")
    @ManyToOne
    private User favoriteUserId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "favoriteUserId")
    private List<Post> favoritePostList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
