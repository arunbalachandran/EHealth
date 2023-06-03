package com.arunbalachandran.ehealth.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * We implement the OncePerRequestFilter since we want all the requests to the server to be checked for authentication.
 * Also, we don't autowire our services here, because Filter classes don't seem to work with the dependency Injection that spring provides.
 * Quoted: Filters usually don't load their own context but rather access service beans.
 * See: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/filter/GenericFilterBean.html
 */
@Component
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER_TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        log.info("Entering JWT Authentication filter...");
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        final String jwtToken;
        final String emailFromJWT;

        if (authHeader == null || !authHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            // pass it on to the next filter instead
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(BEARER_TOKEN_PREFIX.length());
        emailFromJWT = jwtService.extractUsername(jwtToken);

        if (emailFromJWT != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(emailFromJWT);
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // if the user isn't present in the context, add them (previous checks ensure that they are authenticated)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
