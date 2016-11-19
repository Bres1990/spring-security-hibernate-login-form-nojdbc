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

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM USERS WHERE USERNAME = ?1" , nativeQuery = true)
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS u SET u.first_name = ?1 WHERE u.username = ?2", nativeQuery = true)
    void setFixedFirstNameFor(String firstName, String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS u SET u.last_name = ?1 WHERE u.username = ?2", nativeQuery = true)
    void setFixedLastNameFor(String lastName, String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS u SET u.address = ?1 WHERE u.username = ?2", nativeQuery = true)
    void setFixedAddressFor(String address, String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS u SET u.account_no = ?1 WHERE u.username = ?2", nativeQuery = true)
    void setFixedAccountNoFor(String accountNo, String username);
}
