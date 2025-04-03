package com.example.blogbackend.repository;

import com.example.blogbackend.model.Blog;
import com.example.blogbackend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    // Find all blogs with pagination
    Page<Blog> findAll(Pageable pageable);

    // Find blogs by title containing (search functionality)
    Page<Blog> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // Find blogs by user with pagination
    Page<Blog> findByUser(User user, Pageable pageable);

    // Find blogs by user ID
    List<Blog> findByUserId(Long userId);

    // Custom query to find blogs with comments count
    @Query("SELECT b FROM Blog b LEFT JOIN FETCH b.comments WHERE b.id = :id")
    Optional<Blog> findByIdWithComments(@Param("id") Long id);

    // Custom query for popular blogs (by comment count)
    @Query("SELECT b FROM Blog b LEFT JOIN b.comments c GROUP BY b.id ORDER BY COUNT(c.id) DESC")
    Page<Blog> findPopularBlogs(Pageable pageable);
}