package com.exam.spring_security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignupDto {
    private String username;
    private String password;
    private String email;
    private String name;
    private boolean enabled;
}
