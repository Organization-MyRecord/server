package com.mr.myrecord.model.repository;

import com.mr.myrecord.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryCustom {

    User findByUserName(String name);

}
