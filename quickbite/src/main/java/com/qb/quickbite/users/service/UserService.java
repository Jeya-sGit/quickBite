
package com.qb.quickbite.users.service;

import com.qb.quickbite.users.entity.User;
import com.qb.quickbite.users.dto.LoginRequest;
import com.qb.quickbite.users.dto.LoginResponse;
import com.qb.quickbite.users.exception.InvalidCredentialsException;
import com.qb.quickbite.users.dto.UserRequest;
import com.qb.quickbite.users.dto.UserResponse;
import com.qb.quickbite.users.exception.EmailAlreadyExistsException;

import com.qb.quickbite.auth.service.JwtService;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.qb.quickbite.users.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

public UserService(UserRepository userRepository,
                   PasswordEncoder passwordEncoder,
                   JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
}

public UserResponse registerUser(UserRequest request) {

    if (userRepository.existsByEmail(request.getEmail())) {
    throw new EmailAlreadyExistsException("Email already registered");
}

    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPhone(request.getPhone());


    user.setPassword(passwordEncoder.encode(request.getPassword()));
   
    User savedUser = userRepository.save(user);

    return new UserResponse(
        savedUser.getId(),
        savedUser.getName(),
        savedUser.getEmail(),
        savedUser.getPhone()
    );
}

public LoginResponse loginUser(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new InvalidCredentialsException(
                            "Invalid email or password"));

    if (!passwordEncoder.matches(
            request.getPassword(), user.getPassword())) {
        throw new InvalidCredentialsException(
                "Invalid email or password");
    }

    String accessToken = jwtService.generateToken(user.getEmail());

    return new LoginResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            "Login successful",
            accessToken
    );
}


public UserResponse getUserProfile(String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));

    return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPhone()
    );
}


}