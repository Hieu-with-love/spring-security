package com.exam.spring_security.config;

import com.exam.spring_security.model.Customer;
import com.exam.spring_security.repository.UserInfoRepository;
import com.exam.spring_security.service.impl.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserInfoRepository userInfoRepository;
    private final String[] PUBLIC_ENDPOINTS = {"/register", "/user/home", "/forgot-password"};

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserInfoService(userInfoRepository);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.withUsername("hieu")
//                .password(passwordEncoder().encode("123"))
//                .roles("admin")
//                .build();
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder().encode("123"))
//                .roles("user")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/").permitAll()
                                .requestMatchers("/user/new").permitAll()
                                .requestMatchers("/customer/**").authenticated()
                )
                .formLogin(Customizer.withDefaults()).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
