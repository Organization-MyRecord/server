package com.mr.myrecord.service;

import com.mr.myrecord.exception.EmailNotFoundException;
import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.UserRepository;
import com.mr.myrecord.model.request.RegisterRequest;
import com.mr.myrecord.model.response.UserResponse;
import com.mr.myrecord.security.entity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UserResponse read(String email) {
        User resource = userRepository.findByEmail(email);
        UserResponse user = UserResponse.builder()
                .name(resource.getName())
                .age(resource.getAge())
                .content(resource.getContent())
                .image(resource.getImage())
                .build();

        return user;

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

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new EmailNotFoundException("존재하지 않는 이메일 입니다.");
        }
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비번입니다.");
        }
        String accessToken = jwtUtil.createToken(email);
        return accessToken;
    }
}
