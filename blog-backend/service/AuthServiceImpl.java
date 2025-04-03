package com.example.blogbackend.service;




import com.example.blogbackend.dto.LoginDto;
import com.example.blogbackend.dto.RegisterDto;
import com.example.blogbackend.dto.UserProfileDto;
import com.example.blogbackend.exception.BlogAPIException;
import com.example.blogbackend.exception.ResourceNotFoundException;
import com.example.blogbackend.model.User;
import com.example.blogbackend.repository.UserRepository;
import com.example.blogbackend.security.JwtTokenProvider;
import com.example.blogbackend.security.UserPrincipal;

import org.apache.el.stream.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl  {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                         UserRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }


    public User register(RegisterDto registerDto) {
        // Check if email exists
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already taken!");
        }

        // Create new user
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        return userRepository.save(user);
    }

  
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        return tokenProvider.generateToken(authentication);
    }

  
    public User getCurrentUser(UserPrincipal currentUser) {
        return userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));
    }

   
    public UserProfileDto getCurrentUserProfile(UserPrincipal currentUser) {
        User user = getCurrentUser(currentUser);
        return new UserProfileDto(user.getId(), user.getName(), user.getEmail());
    }


    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

	public java.util.Optional<User> findByEmail(String email) {
	    return userRepository.findByEmail(email);
	}
}