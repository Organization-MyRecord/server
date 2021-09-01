package com.mr.myrecord.service;

import com.mr.myrecord.model.entity.Post;
import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.PostRepository;
import com.mr.myrecord.model.repository.UserRepository;
import com.mr.myrecord.model.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MainPageService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public MainPageResponse read(String email) {
        User user = userRepository.findByEmail(email);

        /**
         * 나의 게시물 3개 전달
         */
        List<Post> myPosts = postRepository.findTop3ByUserPostId_IdOrderByPostDateDesc(user.getId());

        List<RecentMyPostResponse> myPostList = myPosts.stream().map(post -> myPostResponse(post))
                .collect(Collectors.toList());

        /**
         * 인기있는 게시물 6개 전달
         */
        List<Post> popularPosts = postRepository.findTop6ByOrderByViewsDesc();

        List<PopularPostResponse> postList = popularPosts.stream().map(post -> popularPostResponse(post))
                .collect(Collectors.toList());

        /**
         * 최신글 게시물 3 개 전달
         */
        List<Post> everyPosts = postRepository.findTop3ByOrderByPostDateDesc();

        List<RecentEveryPostResponse> everyPostList = everyPosts.stream().map(post -> everyPostResponse(post))
                .collect(Collectors.toList());

        /**
         * MainPageResponse에 각각 저장해서 리턴
         */
        MainPageResponse mainPageResponse = MainPageResponse.builder()
                .userImage(user.getImage())
                .recentMyPostResponseList(myPostList)
                .popularPostResponseList(postList)
                .recentEveryPostResponseList(everyPostList)
                .build();

        return mainPageResponse;
    }

    private PopularPostResponse popularPostResponse(Post post) {
        return PopularPostResponse.builder()
                .id(post.getId())
                .userPostId(post.getUserPostId().getId())
                .postName(post.getPostName())
                .postImage(post.getPostImage())
                .content(post.getContent())
                .classification(post.getClassification())
                .views(post.getViews())
                .build();
    }

    private RecentMyPostResponse myPostResponse(Post post) {
        return RecentMyPostResponse.builder()
                .id(post.getId())
                .userPostId(post.getUserPostId().getId())
                .postName(post.getPostName())
                .postImage(post.getPostImage())
                .content(post.getContent())
                .classification(post.getClassification())
                .views(post.getViews())
                .build();
    }

    private RecentEveryPostResponse everyPostResponse(Post post) {
        return RecentEveryPostResponse.builder()
                .id(post.getId())
                .userPostId(post.getUserPostId().getId())
                .postName(post.getPostName())
                .postImage(post.getPostImage())
                .content(post.getContent())
                .classification(post.getClassification())
                .views(post.getViews())
                .build();
    }

    public MainPageResponse unLoginRead() {
        /**
         * 인기있는 게시물 6개 전달
         */
        List<Post> popularPosts = postRepository.findTop6ByOrderByViewsDesc();

        List<PopularPostResponse> postList = popularPosts.stream().map(post -> popularPostResponse(post))
                .collect(Collectors.toList());

        /**
         * 최신글 게시물 3 개 전달
         */
        List<Post> everyPosts = postRepository.findTop3ByOrderByPostDateDesc();

        List<RecentEveryPostResponse> everyPostList = everyPosts.stream().map(post -> everyPostResponse(post))
                .collect(Collectors.toList());

        /**
         * MainPageResponse에 각각 저장해서 리턴
         */
        MainPageResponse mainPageResponse = MainPageResponse.builder()
                .popularPostResponseList(postList)
                .recentEveryPostResponseList(everyPostList)
                .build();

        return mainPageResponse;
    }
}
