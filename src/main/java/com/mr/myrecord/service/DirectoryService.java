package com.mr.myrecord.service;

import com.mr.myrecord.model.entity.Directory;
import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.DirectoryRepository;
import com.mr.myrecord.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DirectoryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DirectoryRepository directoryRepository;

    public boolean create(String email, String name) {
        User user = userRepository.findByEmail(email);

        Directory isOk =  directoryRepository.findByDirectoryNameAndUserDirectoryId(name, user.getId());
        if(isOk == null) {

            Directory directory = Directory.builder()
                    .directoryName(name)
                    .userDirectoryId(user)
                    .postList(new ArrayList<>())
                    .build();

            user.getDirectoryList().add(directory);

            userRepository.save(user);

            return true;
        }
        else return false;
    }

    /**
     * 유저의 id와 directory 이름인 것을 DB에서 찾아서 삭제
     */
    public boolean delete(String email, String name) {
        User user = userRepository.findByEmail(email);

        Directory directory = directoryRepository.findByDirectoryNameAndUserDirectoryId(name, user.getId());
        if(directory != null) {
            directoryRepository.delete(directory);
            return true;
        }
        return false;
    }
}
