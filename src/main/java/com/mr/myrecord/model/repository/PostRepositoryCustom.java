package com.mr.myrecord.model.repository;

import com.mr.myrecord.model.entity.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositoryCustom {
    Post findByPostgg(String name);
}
