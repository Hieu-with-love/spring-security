package com.exam.spring_security.service.impl;

import com.exam.spring_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.exam.spring_security.model.User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Can't find user " + username);
        }
        return new MyUserService(user);
    }
}
