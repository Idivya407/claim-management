package com.example.claim.controller;

import com.example.claim.dto.AuthRequest;
import com.example.claim.dto.AuthResponse;
import com.example.claim.model.Role;
import com.example.claim.model.User;
import com.example.claim.repository.UserRepository;
import com.example.claim.security.CustomUserDetailsService;
import com.example.claim.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) user.setRole(Role.ROLE_CUSTOMER);
        userRepository.save(user);
        return "User registered";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User u = userRepository.findByUsername(request.getUsername()).get();
        String token = jwtUtil.generateToken(u.getUsername(), u.getRole().name());
        return new AuthResponse(token);
    }
}
