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

        post.setPostName(request.getNewPostName())
                .setContent(request.getContent())
                .setPostDate(LocalDateTime.now());

        postRepository.save(post);

        PostUpdateResponse res = PostUpdateResponse.builder()
                .newPostName(post.getPostName())
                .postDate(post.getPostDate())
                .content(post.getContent())
                .postImage(post.getPostImage())
                .build();

        return res;
    }

    public PostReadResponse read(Long postId) throws Exception {
        Post resource = postRepository.findById(postId).orElse(null);
        List<Comment> commentList = commentRepository.findByHello(postId, true);
        List<CommentResponse> commentResponseList = commentList.stream().map(comment -> commentResponse(comment))
                .collect(Collectors.toList());
        if (resource==null) {
            throw new Exception("없는 게시물 id입니다.");
        }
        if (commentList.size()==0) {
            PostReadResponse res = PostReadResponse.builder()
                    .postImage(resource.getPostImage())
                    .classification(resource.getClassification())
                    .content(resource.getContent())
                    .postName(resource.getPostName())
                    .postUserEmail(resource.getPostUserEmail())
                    .views(resource.getViews() + 1L)
                    .id(resource.getId())
                    .commentList(commentResponseList)
                    .build();
            resource.setViews(resource.getViews() + 1L);
            postRepository.save(resource);
            return res;
        }
        else {
            PostReadResponse res2 = PostReadResponse.builder()
                    .postImage(resource.getPostImage())
                    .classification(resource.getClassification())
                    .content(resource.getContent())
                    .postName(resource.getPostName())
                    .postUserEmail(resource.getPostUserEmail())
                    .views(resource.getViews() + 1L)
                    .id(resource.getId())
                    .commentList(commentResponseList)
                    .build();
            resource.setViews(resource.getViews() + 1L);
            postRepository.save(resource);
            return res2;
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
}
