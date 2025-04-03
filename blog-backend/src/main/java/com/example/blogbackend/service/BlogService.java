package com.example.blogbackend.service;

import com.example.blogbackend.dto.BlogDto;
import com.example.blogbackend.exception.ResourceNotFoundException;
import com.example.blogbackend.model.Blog;
import com.example.blogbackend.model.User;
import com.example.blogbackend.repository.BlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Page<Blog> getAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));
    }

    public Blog createBlog(BlogDto blogDto, User user) {
        Blog blog = new Blog();
        blog.setTitle(blogDto.getTitle());
        blog.setContent(blogDto.getContent());
        blog.setUser(user);
        return blogRepository.save(blog);
    }

    public Blog updateBlog(Long id, BlogDto blogDto, User user) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));
        
        if (!blog.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this blog");
        }
        
        blog.setTitle(blogDto.getTitle());
        blog.setContent(blogDto.getContent());
        return blogRepository.save(blog);
    }

    public void deleteBlog(Long id, User user) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));
        
        if (!blog.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this blog");
        }
        
        blogRepository.delete(blog);
    }
}