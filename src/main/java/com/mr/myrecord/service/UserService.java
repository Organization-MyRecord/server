package com.mr.myrecord.service;

import com.mr.myrecord.model.entity.Directory;
import com.mr.myrecord.model.entity.Post;
import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.DirectoryRepository;
import com.mr.myrecord.model.repository.PostRepository;
import com.mr.myrecord.model.repository.UserRepository;
import com.mr.myrecord.model.repository.UserRepositoryCustom;
import com.mr.myrecord.model.request.ChangePw;
import com.mr.myrecord.model.request.CheckPw;
import com.mr.myrecord.model.request.RegisterRequest;
import com.mr.myrecord.model.request.UserUpdateRequest;
import com.mr.myrecord.model.response.*;
import com.mr.myrecord.page.Pagination;
import com.mr.myrecord.security.entity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    MailService mailService;

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
                .description(resource.getContent())
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
                .postDate(post.getPostDate())
                .userPostId(post.getUserPostId().getId())
                .postName(post.getPostName())
                .postUserEmail(post.getPostUserEmail())
                .content(post.getContent())
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

    public UserUpdateResponse update(String email, UserUpdateRequest request) {
        User user = userRepository.findByEmail(email);

        user.setImage(request.getUserImage())
        .setName(request.getUserName())
        .setContent(request.getDescription());

        userRepository.save(user);

        UserUpdateResponse userUpdateResponse = UserUpdateResponse.builder()
                .userName(user.getName())
                .description(user.getContent())
                .userImage(user.getImage())
                .build();

        return userUpdateResponse;

    }

    public LoginResponse login(String token, String email) {
        User user = userRepository.findByEmail(email);
        String image = user.getImage();
        return LoginResponse.builder()
                .isOk(true)
                .description("로그인 되었습니다.")
                .token(token)
                .email(email)
                .image(image)
                .build();
    }

    public String changePw(String email, ChangePw request) {
        User user = userRepository.findByEmail(email);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return "비밀번호가 변경되었습니다.";
    }

    public boolean checkPw(String email, CheckPw request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, request.getPassword())
            );
            return true;
        }catch (Exception e) {
            return false;
        }

    }

    public String sendPw(String email) throws UnsupportedEncodingException, MessagingException {
        mailService.sendTempPwEmail(email);
        return "임시 비밀번호를 이메일로 발송했습니다.";
    }

//    public UserResponse findUser(String name) {
//        User user = userRepository(name);
//        return UserResponse.builder()
//                .age(user.getAge())
//                .id(user.getId())
//                .content(user.getContent())
//                .build();
//    }
}
