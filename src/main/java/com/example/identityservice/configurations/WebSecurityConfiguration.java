package com.example.identityservice.configurations;

import com.example.identityservice.filters.JwtFilter;
import com.example.identityservice.util.OtpAndPhoneNumberAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration{

    @Autowired
    private JwtFilter jwtFilter;

    // Injecting JWT custom authentication provider
    @Autowired
    OtpAndPhoneNumberAuthenticationProvider otpAndPhoneNumberAuthenticationProvider;

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // adding our custom authentication providers
    // authentication manager will call these custom provider's
    // authenticate methods from now on.
    @Autowired
    void registerProvider(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(otpAndPhoneNumberAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    // disabling csrf protection
                    .csrf(csrf -> csrf.disable())
                    // giving permission to every request for /login endpoint
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/v1/identityservice/login").permitAll()
                            .requestMatchers("/v1/identityservice/otp").permitAll()
                            .requestMatchers("/v1/identityservice/refresh").permitAll()
                            .requestMatchers("/v1/identityservice/roles").permitAll()
                            .requestMatchers("/v1/identityservice/roles").permitAll()
                            .requestMatchers("/v2/api-docs").permitAll()
                             .requestMatchers("/swagger-ui.html").permitAll()
                            // .requestMatchers("/v1/identityservice/register").permitAll()
                            // .requestMatchers("/v1/identityservice/register").authenticated()
                            .anyRequest().authenticated()
                    )
                    // setting stateless session, because we choose to implement Rest API
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    );

            // adding the custom filter before UsernamePasswordAuthenticationFilter in the filter chain
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }
    }

