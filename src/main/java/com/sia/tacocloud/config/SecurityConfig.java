package com.sia.tacocloud.config;

import com.sia.tacocloud.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Configuration
public class SecurityConfig {

    public static final String DESIGN_ENDPOINT = "/design";
    public static final String ORDERS_ENDPOINT = "/orders";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException(format("User '%s' not found", username)));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(DESIGN_ENDPOINT, ORDERS_ENDPOINT).hasRole("USER")
                        .requestMatchers("/", "/**").permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl(DESIGN_ENDPOINT))
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .build();
    }
}
