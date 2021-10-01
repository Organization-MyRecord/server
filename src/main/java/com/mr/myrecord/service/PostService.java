package com.mr.myrecord.service;

import com.mr.myrecord.model.entity.Comment;
import com.mr.myrecord.model.entity.Directory;
import com.mr.myrecord.model.entity.Post;
import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.CommentRepository;
import com.mr.myrecord.model.repository.DirectoryRepository;
import com.mr.myrecord.model.repository.PostRepository;
import com.mr.myrecord.model.repository.UserRepository;
import com.mr.myrecord.model.request.PostRequest;
import com.mr.myrecord.model.request.PostUpdateRequest;
import com.mr.myrecord.model.response.*;
import com.mr.myrecord.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DirectoryRepository directoryRepository;

    @Autowired
    private CommentRepository commentRepository;
    /**
     * post 생성 api
     */
    public PostResponse create(String email, PostRequest request) throws Exception {
        User user = userRepository.findByEmail(email);
        Directory directory = directoryRepository.findByDirectoryNameAndUserDirectoryId(request.getDirectoryName(), user.getId());
        // TODO: 에러처리
//        if (directory == null) {
//            throw new Exception("디렉토리를 지정하지 않았습니다.");
//        }

        Post newPost = Post.builder()
                .postName(request.getPostName())
                .postDate(LocalDateTime.now())
                .classification(user.getField())
                .content(request.getContent())
                .postImage(request.getPostImage())
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
                .postImage(newPost.getPostImage())
                .postUserEmail(newPost.getPostUserEmail())
                .build();
        return postResponse;
    }

    public PostUpdateResponse update(String email, PostUpdateRequest request) {
        User user = userRepository.findByEmail(email);
        Post post = postRepository.findByUserPostIdAndPostId(user.getId(), request.getPostId());
        Directory directory = directoryRepository.findByDirectoryNameAndUserDirectoryId(request.getDirectoryName(), user.getId());

        post.setPostName(request.getNewPostName())
                .setContent(request.getContent())
                .setPostDate(LocalDateTime.now())
                .setDirectoryId(directory);

        postRepository.save(post);

        PostUpdateResponse res = PostUpdateResponse.builder()
                .newPostName(post.getPostName())
                .postDate(post.getPostDate())
                .content(post.getContent())
                .postImage(post.getPostImage())
                .directoryName(directory.getDirectoryName())
                .build();

        return res;
    }

    public PostReadResponse read(Long postId) throws Exception {
        Post resource = postRepository.findById(postId).orElse(null);
        Directory directory = directoryRepository.findById(resource.getDirectoryId().getId()).orElse(null);
        if (resource==null) {
            throw new Exception("없는 게시물 id입니다.");
        }

        PostReadResponse res = PostReadResponse.builder()
                .directoryName(directory.getDirectoryName())
                .postImage(resource.getPostImage())
                .classification(resource.getClassification())
                .content(resource.getContent())
                .postName(resource.getPostName())
                .postUserEmail(resource.getPostUserEmail())
                .views(resource.getViews() + 1L)
                .id(resource.getId())
                .build();
        resource.setViews(resource.getViews() + 1L);
        postRepository.save(resource);
        return res;

    }

    public boolean delete(Long postId) {

        try {
            postRepository.deleteById(postId);
        }catch(Exception e) {
            return false;
        }
        return true;
    }

    public FieldPostResponse fieldPost(String field, Pageable pageable) {

        Page<Post> posts = postRepository.findByClassification(field, pageable);

        List<PostResponse> postList = posts.stream().map(post -> postPageResponse(post, field))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(posts.getTotalPages())
                .totalElements(posts.getTotalElements())
                .currentPage(posts.getNumber()+1)
                .currentElements(posts.getNumberOfElements())
                .build();

        return FieldPostResponse.builder()
                .myPostList(postList)
                .postPagination(pagination)
                .build();
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


    private PostResponse postPageResponse(Post post, String field) {
        return PostResponse.builder()
                .id(post.getId())
                .postDate(post.getPostDate())
                .userPostId(post.getUserPostId().getId())
                .postImage(post.getPostImage())
                .postName(post.getPostName())
                .postUserEmail(post.getPostUserEmail())
                .content(post.getContent())
                .classification(field)
                .views(post.getViews())
                .build();
    }

    private PostResponse searchPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .postDate(post.getPostDate())
                .userPostId(post.getUserPostId().getId())
                .postImage(post.getPostImage())
                .postName(post.getPostName())
                .postUserEmail(post.getPostUserEmail())
                .content(post.getContent())
                .classification(post.getClassification())
                .views(post.getViews())
                .build();
    }

    public AnotherPostResponse anotherPost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);

        List<Post> postUpperList = postRepository.findTop2ByUserPostId_IdAndPostDateGreaterThanOrderByPostDateAsc(post.getUserPostId().getId(), post.getPostDate());
        List<Post> postUnderList = postRepository.findTop2ByUserPostId_IdAndPostDateLessThanOrderByPostDateDesc(post.getUserPostId().getId(), post.getPostDate());

        // 이후 쓴 글 2개
        List<PostUpperResponse> postUpperResponseList = postUpperList.stream().map(upperPost -> upperResponse(upperPost))
                .collect(Collectors.toList());
        // 이전 쓴 글 2개
        List<PostUnderResponse> postUnderResponseList = postUnderList.stream().map(upperPost -> underResponse(upperPost))
                .collect(Collectors.toList());

        // 이전글이 위로 올라가게 설정
        Collections.reverse(postUnderResponseList);

        return AnotherPostResponse.builder()
                .postUpperResponseList(postUpperResponseList)
                .postUnderResponseList(postUnderResponseList)
                .build();
    }

    private PostUpperResponse upperResponse(Post post) {
        return PostUpperResponse.builder()
                .postId(post.getId())
                .postName(post.getPostName())
                .postDate(post.getPostDate())
                .build();
    }
    private PostUnderResponse underResponse(Post post) {
        return PostUnderResponse.builder()
                .postId(post.getId())
                .postName(post.getPostName())
                .postDate(post.getPostDate())
                .build();

    }

    public FieldPostResponse searchPost(String keyword, Pageable pageable) {
        Page<Post> posts = postRepository.findByPostlist(keyword, pageable);

        List<PostResponse> postList = posts.stream().map(post -> searchPostResponse(post))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(posts.getTotalPages())
                .totalElements(posts.getTotalElements())
                .currentPage(posts.getNumber()+1)
                .currentElements(posts.getNumberOfElements())
                .build();

        return FieldPostResponse.builder()
                .myPostList(postList)
                .postPagination(pagination)
                .build();
    }

    /**
     * 디렉토리 클릭시 디렉토리안 게시물들 불러오기 페이징 처리
     * @param directoryName
     * @param userEmail
     * @param pageable
     * @return
     */
    public DirectoryPostResponse readPost(String directoryName, String userEmail, Pageable pageable) {
        Page<Post> posts = postRepository.findByPostUserEmailAndDirectoryId_DirectoryName(userEmail, directoryName, pageable);

        List<PostResponse> postList = posts.stream().map(post -> searchPostResponse(post))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(posts.getTotalPages())
                .totalElements(posts.getTotalElements())
                .currentPage(posts.getNumber()+1)
                .currentElements(posts.getNumberOfElements())
                .build();

        return DirectoryPostResponse.builder()
                .postResponseList(postList)
                .pagination(pagination)
                .build();

    }
}
