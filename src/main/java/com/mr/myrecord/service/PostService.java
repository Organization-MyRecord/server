package com.mr.myrecord.service;

import com.mr.myrecord.model.entity.Directory;
import com.mr.myrecord.model.entity.Post;
import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.DirectoryRepository;
import com.mr.myrecord.model.repository.PostRepository;
import com.mr.myrecord.model.repository.UserRepository;
import com.mr.myrecord.model.request.PostRequest;
import com.mr.myrecord.model.request.PostUpdateRequest;
import com.mr.myrecord.model.response.PostReadResponse;
import com.mr.myrecord.model.response.PostResponse;
import com.mr.myrecord.model.response.PostUpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DirectoryRepository directoryRepository;

    /**
     * post 생성 api
     */
    public PostResponse create(String email, PostRequest request) {
        User user = userRepository.findByEmail(email);
        Directory directory = directoryRepository.findByDirectoryNameAndUserDirectoryId(request.getDirectoryName(), user.getId());

        Post newPost = Post.builder()
                .postName(request.getPostName())
                .postDate(LocalDateTime.now())
                .classification(user.getField())
                .content(request.getContent())
                .views(0L)
                .postUserEmail(email)
                .directoryId(directory)
                .userPostId(user)
                .commentList(new ArrayList<>())
                .build();

        postRepository.save(newPost);

        PostResponse postResponse = PostResponse.builder()
                .id(newPost.getId())
                .postName(newPost.getPostName())
                .classification(newPost.getClassification())
                .content(newPost.getContent())
                .views(newPost.getViews())
                .userPostId(newPost.getUserPostId().getId())
                .build();
        return postResponse;
    }

    public PostUpdateResponse update(String email, PostUpdateRequest request) {
        User user = userRepository.findByEmail(email);
        Directory directory = directoryRepository.findByDirectoryNameAndUserDirectoryId(request.getDirectoryName(), user.getId());
        Post post = postRepository.findByDirectoryIdAndUserPostIdAndPostName(directory.getId(), user.getId(), request.getPostName());

        post.setPostName(request.getNewPostName())
                .setContent(request.getContent())
                .setPostDate(LocalDateTime.now());

        postRepository.save(post);

        PostUpdateResponse res = PostUpdateResponse.builder()
                .newPostName(post.getPostName())
                .postDate(post.getPostDate())
                .content(post.getContent())
                .build();

        return res;
    }

    public PostReadResponse read(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        if (post!=null) {
            Post resource = post.get();
            PostReadResponse res = PostReadResponse.builder()
                    .classification(resource.getClassification())
                    .content(resource.getContent())
                    .postName(resource.getPostName())
                    .postUserEmail(resource.getPostUserEmail())
                    .views(resource.getViews()+1L)
                    .id(resource.getId())
                    .build();
            resource.setViews(resource.getViews()+1L);
            postRepository.save(resource);
            return res;
        }
        else {
            return new PostReadResponse();
        }
    }

    public boolean delete(Long postId) {

        try {
            postRepository.deleteById(postId);
        }catch(Exception e) {
            return false;
        }
        return true;
    }
}
