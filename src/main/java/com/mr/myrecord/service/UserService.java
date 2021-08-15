package com.mr.myrecord.service;

import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.UserRepository;
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
}
