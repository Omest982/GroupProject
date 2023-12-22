package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.service.impl.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                                .anyRequest().permitAll()
//                        .requestMatchers("/", "/home").permitAll()
//                        .anyRequest().authenticated()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService);
    }
}
