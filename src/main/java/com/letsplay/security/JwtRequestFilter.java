package com.letsplay.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtRequestFilter is a custom filter that intercepts every incoming request.
 * It extracts the JWT from the Authorization header, validates it, and sets the authentication
 * in the SecurityContext, so that Spring Security knows who the authenticated user is.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    /**
     * Spring will automatically inject an instance of UserDetailsServiceImpl here.
     * This service is used to load user-specific data during authentication.
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Spring will automatically inject an instance of JwtUtil here.
     * This utility is used for generating and validating JWTs.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * This method is executed for every incoming request.
     * It checks for a JWT in the Authorization header, validates it, and sets up the security context.
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @param filterChain The filter chain to proceed with.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Check if the Authorization header exists and starts with "Bearer ".
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Extract the JWT token (remove "Bearer ")
            username = jwtUtil.extractUsername(jwt);
        }

        // If a username is extracted and no authentication is currently set in the SecurityContext,
        // then validate the token and set the authentication.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {

                // Create an authentication token.
                // The authorities are currently empty, they should be populated based on the user's roles.
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Set the authentication in the SecurityContext.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // Continue with the filter chain.
        filterChain.doFilter(request, response);
    }
}
