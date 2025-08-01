package com.sia.taco.cloud.server.config;

import com.sia.taco.cloud.server.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    public static final String DESIGN_ENDPOINT = "/design";
    public static final String ORDERS_ENDPOINT = "/orders";
    public static final String INGREDIENTS_ENDPOINT = "/api/ingredients/*";

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
                        .requestMatchers(HttpMethod.POST, INGREDIENTS_ENDPOINT).hasAuthority("SCOPE_writeIngredients")
                        .requestMatchers(HttpMethod.DELETE, INGREDIENTS_ENDPOINT).hasAuthority("SCOPE_deleteIngredients")
                        .requestMatchers("/", "/**").permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/tacos/*", INGREDIENTS_ENDPOINT))
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl(DESIGN_ENDPOINT))
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }
}
