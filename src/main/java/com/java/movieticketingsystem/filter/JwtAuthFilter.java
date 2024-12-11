package com.java.movieticketingsystem.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.movieticketingsystem.auth.helper.JwtService;
import com.java.movieticketingsystem.auth.helper.UserInfoService;
import com.java.movieticketingsystem.utils.RestResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.java.movieticketingsystem.utils.constants.ExceptionConstants.AUTH_FAILED;
import static com.java.movieticketingsystem.utils.constants.ExceptionConstants.AUTH_TOKEN_EXPIRED;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Retrieve the Authorization header
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;

            // Check if the header starts with "Bearer "
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7); // Extract token
                username = jwtService.extractUsername(token); // Extract username from token
            }

            // If the token is valid and no authentication is set in the context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validate token and set authentication
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {
            // Detailed logging of expired JWT
            logger.error("JWT Token has expired: {}", ex.getMessage());

            // Create a RestResponse for expired token
            RestResponse restResponse = new RestResponse();
            restResponse.setStatus(false);
            restResponse.setMessage(AUTH_TOKEN_EXPIRED);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(
                    objectMapper.writeValueAsString(restResponse)
            );
        } catch (Exception ex) {
            // Catch and log any other unexpected exceptions
            logger.error("Unexpected error in JWT authentication", ex);

            // Create a generic error response
            RestResponse restResponse = new RestResponse();
            restResponse.setStatus(false);
            restResponse.setMessage(AUTH_FAILED);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(
                    objectMapper.writeValueAsString(restResponse)
            );
        }
    }
}
