package com.mr.myrecord.model.repository;

import com.mr.myrecord.model.entity.Post;
import static com.mr.myrecord.model.entity.QPost.post;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    @Autowired
    EntityManager entityManager;

    @Override
    public Post findByPostgg(String name) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(post).where(post.postName.eq(name)).fetchOne();
    }
}
