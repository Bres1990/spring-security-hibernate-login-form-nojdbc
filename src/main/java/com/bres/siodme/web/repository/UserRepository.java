package com.bres.siodme.web.repository;

import com.bres.siodme.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Adam on 2016-07-30.
 */

/* In JPA you don't have to write the implementation of a repository interface */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM USERS WHERE USERNAME = ?1" , nativeQuery = true)
    User findByUsername(String username);
}
