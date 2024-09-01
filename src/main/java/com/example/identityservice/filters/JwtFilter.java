package com.example.identityservice.filters;

import com.example.identityservice.customexceptions.InvalidJwtToken;
import com.example.identityservice.model.ApiResponse;
import com.example.identityservice.service.OtpAndPhoneNumberUserDetailsService;
import com.example.identityservice.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secretKey}")
    private String jwtSecret;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    OtpAndPhoneNumberUserDetailsService otpAndPhoneNumberUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);

            try {
                if (jwtUtil.validateAccessToken(jwtToken)) {
                    String username = jwtUtil.getUsername(jwtToken);
                    UserDetails userDetails=otpAndPhoneNumberUserDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationtoken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                    authenticationtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //Set the authentication token to Spring Security Context
                    SecurityContextHolder.getContext().setAuthentication(authenticationtoken);
                }
                else
                {
                    throw new InvalidJwtToken("Invalid Access Token. Token is not valid or expired");
                }
            }
            catch (UsernameNotFoundException e) {
                ApiResponse apiResponse = ApiResponse.error(e.getMessage());
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(apiResponse);
                response.setContentType("application/json");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(json);
            }
            catch (Exception e) {
                ApiResponse apiResponse = ApiResponse.error(e.getMessage());
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(apiResponse);
                response.setContentType("application/json");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(json);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
