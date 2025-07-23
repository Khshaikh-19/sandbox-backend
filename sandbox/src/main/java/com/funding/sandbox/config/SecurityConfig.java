package com.funding.sandbox.config;

import com.funding.sandbox.service.CustomerUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor //  annotation for creating a constructor with all final fields
public class SecurityConfig {

    // Dependencies are injected via the constructor by Lombok
    private final CustomerUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;

    // This is the main security configuration bean that defines our security rules.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF protection, as we are not using sessions for a stateless API.
                .csrf(csrf -> csrf.disable())

                // 2. Define the authorization rules for our endpoints.
                .authorizeHttpRequests(auth -> auth
                        // Endpoints that are public and do not require authentication.
                        .requestMatchers("/api/register", "/api/auth/login").permitAll()
                        // Endpoints that require a specific role.
                        .requestMatchers("/api/startups/profile").hasRole("STARTUP")
                        // Any other request that hasn't been matched must be authenticated.
                        .anyRequest().authenticated()
                )

                // 3. Set the session management policy to STATELESS.
                // This tells Spring Security not to create or use HttpSessions.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. Register our custom AuthenticationProvider.
                .authenticationProvider(authenticationProvider())

                // 5. Add our custom JWT filter to the chain, ensuring it runs before the standard auth filter.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Bean that provides the data access object for authentication.
    // It tells Spring Security how to find users (via UserDetailsService) and how to check passwords (via PasswordEncoder).
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Our custom user finding logic
        authProvider.setPasswordEncoder(passwordEncoder());     // Our password hashing logic
        return authProvider;
    }

    // Bean that manages the overall authentication process.
    // We need to expose  we can use it in our custom login endpoint.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Bean that provides the password hashing algorithm (BCrypt).
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}