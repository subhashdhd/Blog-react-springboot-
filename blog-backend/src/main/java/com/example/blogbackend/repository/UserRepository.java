package com.example.blogbackend.repository;

import com.example.blogbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check if email exists
    boolean existsByEmail(String email);

    // Custom query to find user with blogs
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.blogs WHERE u.id = :userId")
    Optional<User> findByIdWithBlogs(@Param("userId") Long userId);

    // Custom query to find user with blogs and comments
    @Query("SELECT DISTINCT u FROM User u " +
           "LEFT JOIN FETCH u.blogs b " +
           "LEFT JOIN FETCH b.comments c " +
           "WHERE u.id = :userId")
    Optional<User> findByIdWithBlogsAndComments(@Param("userId") Long userId);
}