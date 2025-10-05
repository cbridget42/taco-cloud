package com.sia.taco.cloud.authorization.config;

import com.sia.taco.cloud.authorization.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http.with(OAuth2AuthorizationServerConfigurer.authorizationServer(), Customizer.withDefaults());
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        return http
                .securityMatcher(
                        "/oauth2/**",
                        "/login/**",
                        "/.well-known/**"
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(
                        "/oauth2/token",
                        "/oauth2/introspect",
                        "/oauth2/revoke"
                ))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/oauth2/token").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        return http
                .securityMatcher("/**")
                .authorizeHttpRequests(authorize -> authorize
                                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login"))
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .build();
    }
    
    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException(format("User '%s' not found", username)));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
