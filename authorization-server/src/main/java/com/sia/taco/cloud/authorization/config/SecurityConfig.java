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

    public static final String DESIGN_ENDPOINT = "/design";
    public static final String ORDERS_ENDPOINT = "/orders";

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
                                .anyRequest().authenticated()
//                        authz -> authz
//                        .requestMatchers(DESIGN_ENDPOINT, ORDERS_ENDPOINT).hasRole("USER")
//                        .requestMatchers("/", "/**").permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/tacos/*", "/api/ingredients/*"))
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl(DESIGN_ENDPOINT))
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
