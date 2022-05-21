package com.mylibraryproject.repository;

import com.mylibraryproject.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.username = ?1")
    Optional<Users> findByUserName(String userName);

    Optional<Users> findById(Long id);
}
