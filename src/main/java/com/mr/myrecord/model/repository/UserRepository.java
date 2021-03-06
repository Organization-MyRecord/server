package com.mr.myrecord.model.repository;

import com.mr.myrecord.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email);

    @Query("select count(u.id) from User u where u.favoriteUserId.id = ?1")
    Long findFavoriteUserNum(Long id);
}
