package com.mr.myrecord.service;

import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.UserRepository;
import com.mr.myrecord.model.request.RegisterRequest;
import com.mr.myrecord.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse read(Long id) {
        User resource = userRepository.findById(id).orElse(null);
        UserResponse user = UserResponse.builder()
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
                .password(request.getPassword())
                .gender(request.getGender())
                .name(request.getName())
                .job(request.getJob())
                .major(request.getMajor())
                .age(request.getAge())
                .reportedCount(3)
                .reporterCount(3)
                .build();

        return userRepository.save(user);
    }
}
