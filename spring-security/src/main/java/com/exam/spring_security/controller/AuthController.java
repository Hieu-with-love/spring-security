package com.exam.spring_security.controller;

import com.exam.spring_security.dto.LoginDto;
import com.exam.spring_security.dto.SignupDto;
import com.exam.spring_security.model.Role;
import com.exam.spring_security.model.User;
import com.exam.spring_security.repository.RoleRepository;
import com.exam.spring_security.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("User signed in successfully." + authentication.getPrincipal());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signupDto){
        if (userRepository.existsByUsername(signupDto.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }

        if (userRepository.existsByEmail(signupDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already taken.");
        }

        User user = User.builder()
                .name(signupDto.getName())
                .username(signupDto.getUsername())
                .email(signupDto.getEmail())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .enabled(true)
                .build();
        Role role = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singleton(role));
        return ResponseEntity.ok("Register success" + userRepository.save(user));
    }
}
