package com.mr.myrecord.service;

import com.mr.myrecord.model.entity.Directory;
import com.mr.myrecord.model.entity.Post;
import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.DirectoryRepository;
import com.mr.myrecord.model.repository.PostRepository;
import com.mr.myrecord.model.repository.UserRepository;
import com.mr.myrecord.model.request.RegisterRequest;
import com.mr.myrecord.model.response.DirectoryResponse;
import com.mr.myrecord.model.response.PageResponse;
import com.mr.myrecord.model.response.PostResponse;
import com.mr.myrecord.page.Pagination;
import com.mr.myrecord.security.entity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DirectoryRepository directoryRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public PageResponse read(String email, Pageable pageable) {
        User resource = userRepository.findByEmail(email);

        List<Directory> directories = directoryRepository.findByUserId(resource.getId());
        List<DirectoryResponse> directoryList = directories
                .stream()
                .map(directory -> directoryResponse(directory))
                .collect(Collectors.toList());

        Page<Post> posts = postRepository.findByUserPostId(resource.getId(), pageable);

        List<PostResponse> postList = posts.stream().map(post -> postPageResponse(post, resource.getField()))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(posts.getTotalPages())
                .totalElements(posts.getTotalElements())
                .currentPage(posts.getNumber()+1)
                .currentElements(posts.getNumberOfElements())
                .build();

        PageResponse myPage = PageResponse.builder()
                .name(resource.getName())
                .content(resource.getContent())
                .major(resource.getMajor())
                .detailMajor(resource.getDetailMajor())
                .field(resource.getField())
                .image(resource.getImage())
                .age(resource.getAge())
                .email(resource.getEmail())
                .postNum(postRepository.findPostNum(resource.getId()))
                .favoriteUserNum(userRepository.findFavoriteUserNum(resource.getId()))
                .myPostList(postList)
                .directoryList(directoryList)
                .postPagination(pagination)
                .build();

        return myPage;

    }

    private DirectoryResponse directoryResponse(Directory directory) {
        List<Post> posts = directory.getPostList();
        List<PostResponse> postList = posts.stream().map(post -> postResponse(post))
                .collect(Collectors.toList());

        return DirectoryResponse.builder()
                .directoryName(directory.getDirectoryName())
                .postList(postList)
                .build();
    }

    private PostResponse postResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .userPostId(post.getUserPostId().getId())
                .postName(post.getPostName())
                .postUserEmail(post.getPostUserEmail())
                .content(post.getContent())
                .build();
    }

    private PostResponse postPageResponse(Post post, String field) {
        return PostResponse.builder()
                .id(post.getId())
                .userPostId(post.getUserPostId().getId())
                .postName(post.getPostName())
                .postUserEmail(post.getPostUserEmail())
                .content(post.getContent())
                .classification(field)
                .views(post.getViews())
                .build();
    }

    public boolean emailCheck(String email) {
        User user = userRepository.findByEmail(email);
        if(user != null) { //존재하는 email 이면
            return false;
        }
        return true;
    }

    public User create(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .gender(request.getGender())
                .field(request.getField())
                .name(request.getName())
                .birth(request.getBirth())
                .major(request.getMajor())
                .detailMajor(request.getDetailMajor())
                .major(request.getMajor())
                .age(request.getAge())
                .reportedCount(3)
                .reporterCount(3)
                .build();

        return userRepository.save(user);
    }
}
