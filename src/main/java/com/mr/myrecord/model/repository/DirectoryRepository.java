package com.mr.myrecord.model.repository;

import com.mr.myrecord.model.entity.Directory;
import com.mr.myrecord.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {

    @Query("select u from Directory u where u.directoryName=?1 and u.userDirectoryId.id=?2")
    Directory findByDirectoryNameAndUserDirectoryId(String directoryName, Long id);

    @Query("select u from Directory u where u.userDirectoryId.id = ?1")
    List<Directory> findByUserId(Long id);
}
