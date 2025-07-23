package com.funding.sandbox.config; // or com.funding.sandbox.filter

import com.funding.sandbox.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Marks this as a Spring bean
@RequiredArgsConstructor // for a constructor with all final fields
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Check if the request has an Authorization header, and if it's a Bearer token.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // If not, pass to the next filter and exit.
            return;
        }

        // 2. Extract the token from the header.
        jwt = authHeader.substring(7); // "Bearer ".length() is 7

        // 3. Extract the user's email from the token.
        userEmail = jwtService.extractUsername(jwt);

        // 4. If we have a user email AND the user is not already authenticated...
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // ...load the user details from the database.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 5. If the token is valid for this user...
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // ...create an authentication token and set it in the Security Context.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // We don't need credentials
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response); // Pass to the next filter.
    }
}