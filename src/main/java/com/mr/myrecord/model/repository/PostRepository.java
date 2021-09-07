package com.mr.myrecord.model.repository;

import com.mr.myrecord.model.entity.Directory;
import com.mr.myrecord.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select count(u.id) from Post u where u.userPostId.id = ?1")
    Long findPostNum(Long id);

    @Query("SELECT u FROM Post u  WHERE u.userPostId.id = ?1")
    Page<Post> findByUserPostId(Long id, Pageable pageable);

    List<Post> findTop6ByOrderByViewsDesc();

    List<Post> findTop3ByUserPostId_IdOrderByPostDateDesc(Long userPostId);

    List<Post> findTop3ByOrderByPostDateDesc();

    @Query("select u from Post u where u.directoryId.id=?1 and u.userPostId.id=?2 and u.postName=?3")
    Post findByDirectoryIdAndUserPostIdAndPostName(Long directoryId, Long id, String postName);
}
