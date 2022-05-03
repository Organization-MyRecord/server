package com.mr.myrecord;

import com.mr.myrecord.model.entity.GenderEnum;
import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.DirectoryRepository;
import com.mr.myrecord.model.repository.PostRepository;
import com.mr.myrecord.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class Runner implements ApplicationRunner {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    DirectoryRepository directoryRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        try(Connection connection = dataSource.getConnection()) {
//            User user = User.builder()
//                    .id(1L)
//                    .age(11)
//                    .birth("1996-**-**")
//                    .detailMajor("컴퓨터공학")
//                    .content("기억보단 기록")
//                    .email("test@naver.com")
//                    .field("IT/웹/통신")
//                    .gender(GenderEnum.남)
//                    .image("string")
//                    .major("공학")
//                    .name("나는야 퉁퉁이")
//                    .password(passwordEncoder.encode("string"))
//                    .reporterCount(3)
//                    .reportedCount(3)
//                    .build();
//
//            userRepository.save(user);

//            Directory directory1 = Directory.builder()
//                    .id(1L)
//                    .directoryName("hello")
//                    .userDirectoryId(user).build();
//
//            directoryRepository.save(directory1);
//
//            Directory directory2 = Directory.builder()
//                    .id(2L)
//                    .directoryName("hello")
//                    .userDirectoryId(user).build();
//
//            directoryRepository.save(directory2);
//
//            Post post1 = Post.builder()
//                    .id(1L)
//                    .classification("IT/웹/통신")
//                    .content("글쓰기")
//                    .postDate(LocalDateTime.now())
//                    .postImage("이미지")
//                    .views(3L)
//                    .directoryId(directory1)
//                    .userPostId(user)
//                    .build();
//
//            postRepository.save(post1);
//
//            Post post2 = Post.builder()
//                    .id(1L)
//                    .classification("IT/웹/통신")
//                    .content("글쓰기")
//                    .postDate(LocalDateTime.now())
//                    .postImage("이미지")
//                    .views(3L)
//                    .directoryId(directory2)
//                    .userPostId(user)
//                    .build();
//
//            postRepository.save(post2);
//        }
    }


}
