
package com.example.blogbackend.service;

import com.example.blogbackend.dto.UserProfileDto;
import com.example.blogbackend.exception.ResourceNotFoundException;
import com.example.blogbackend.model.User;
import com.example.blogbackend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }


    public UserProfileDto getUserProfile(Long id) {
        User user = getUserById(id);
        return new UserProfileDto(user.getId(), user.getName(), user.getEmail());
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}