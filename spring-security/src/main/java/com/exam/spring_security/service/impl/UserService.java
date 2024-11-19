package com.exam.spring_security.service.impl;

import com.exam.spring_security.model.UserInfo;
import com.exam.spring_security.repository.UserInfoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record UserService(UserInfoRepository repository,
                          PasswordEncoder passwordEncoder) {
    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "Them user thanh cong !";
    }
}
