package com.mr.myrecord.model.repository;

import com.mr.myrecord.model.entity.Directory;
import com.mr.myrecord.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    @Query("select count(u.id) from Post u where u.userPostId.id = ?1")
    Long findPostNum(Long id);

    @Query("SELECT u FROM Post u  WHERE u.userPostId.id = ?1")
    Page<Post> findByUserPostId(Long id, Pageable pageable);

    @Query("SELECT u FROM Post u  WHERE u.classification = ?1")
    Page<Post> findByClassification(String field, Pageable pageable);


    Page<Post> findByPostUserEmailAndDirectoryId_DirectoryName(String email, String directoryName, Pageable pageable);

    @Query("select count(u) from Post u where u.postUserEmail=?1 and u.directoryId.directoryName=?2")
    Long findByDirectoryPostCount(String email, String directoryName);

    List<Post> findTop8ByOrderByViewsDesc();

    List<Post> findTop4ByUserPostId_IdOrderByPostDateDesc(Long userPostId);

    List<Post> findTop3ByOrderByPostDateDesc();

    @Query("select u from Post u where u.userPostId.id=?1 and u.id=?2")
    Post findByUserPostIdAndPostId(Long id, Long postId);

    @Query("select u from Post u where u.directoryId.id = ?1")
    Post findByDirectoryId(Long id);

    List<Post> findTop2ByUserPostId_IdAndPostDateGreaterThanOrderByPostDateAsc(Long userId, LocalDateTime postDate);

    List<Post> findTop2ByUserPostId_IdAndPostDateLessThanOrderByPostDateDesc(Long userId, LocalDateTime postDate);

    //search 기능 DB불러오기
    @Query("select u from Post u where u.postName like %?1% or u.content like %?1%")
    Page<Post> findByPostlist(String keyword, Pageable pageable);
}
