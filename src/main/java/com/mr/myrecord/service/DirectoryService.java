package com.mr.myrecord.service;

import com.mr.myrecord.model.entity.Directory;
import com.mr.myrecord.model.entity.Post;
import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.DirectoryRepository;
import com.mr.myrecord.model.repository.PostRepository;
import com.mr.myrecord.model.repository.UserRepository;
import com.mr.myrecord.model.request.DirectoryRequest;
import com.mr.myrecord.model.response.DirectoryList;
import com.mr.myrecord.model.response.DirectoryListResponse;
import com.mr.myrecord.model.response.RecentMyPostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DirectoryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DirectoryRepository directoryRepository;

    public boolean create(String email, DirectoryRequest request) {
        User user = userRepository.findByEmail(email);
        String name = request.getName();

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
        Post post = postRepository.findByDirectoryId(directory.getId());
        if(directory != null && post == null) {
            directoryRepository.delete(directory);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean update(String email, String name, DirectoryRequest directoryRequest) {
        User user = userRepository.findByEmail(email);
        Directory directory = directoryRepository.findByDirectoryNameAndUserDirectoryId(name, user.getId());
        if (directoryRequest.getName() == "") return false;
        directory.setDirectoryName(directoryRequest.getName());
        directoryRepository.save(directory);
        return true;
    }

    public DirectoryListResponse read(String email) {
        User user = userRepository.findByEmail(email);
        List<Directory> directoryList = directoryRepository.findByUserId(user.getId());

        List<DirectoryList> myDirectoryList = directoryList.stream().map(directory -> response(email, directory) )
                .collect(Collectors.toList());

        return DirectoryListResponse.builder()
                .directoryList(myDirectoryList)
                .build();
    }
    public DirectoryList response(String email, Directory directory) {
        return DirectoryList.builder()
                .count(postRepository.findByDirectoryPostCount(email, directory.getDirectoryName()))
                .directoryName(directory.getDirectoryName())
                .build();


    }

}
