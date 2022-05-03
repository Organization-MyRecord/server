package com.mr.myrecord.model.repository;

import static com.mr.myrecord.model.entity.QUser.user;
import com.mr.myrecord.model.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRespositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public User findByUserName(String name) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.name.eq("name"));

        User u = queryFactory.selectFrom(user)
                .where(builder)
                .fetchOne();

        return u;
    }
}
