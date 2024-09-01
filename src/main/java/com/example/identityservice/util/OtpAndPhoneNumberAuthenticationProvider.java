package com.example.identityservice.util;

import com.example.identityservice.model.OtpPhoneNumber;
import com.example.identityservice.repository.OtpRepository;
import com.example.identityservice.security.OtpAndPhoneNumberAuthenticationToken;
import com.example.identityservice.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class OtpAndPhoneNumberAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private OtpService otpService;

    @Override
    public Authentication authenticate(Authentication authentication){
        String phoneNumber = authentication.getName();
        String otp = authentication.getCredentials().toString();

        // Validate the OTP and phone number
        if (!isValidOtpForPhoneNumber(phoneNumber, otp)) {
            otpService.markOtpAsUsedByPhoneNumber(phoneNumber);
            throw new BadCredentialsException("Invalid OTP or phone number");
        }

        otpService.markOtpAsUsedByPhoneNumber(phoneNumber);

        // Load user details from UserDetailsService
        UserDetails employee;
            employee = userDetailsService.loadUserByUsername(phoneNumber);

        // Return an authenticated token with user details and authorities
        return new OtpAndPhoneNumberAuthenticationToken(employee,null, employee.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAndPhoneNumberAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private boolean isValidOtpForPhoneNumber(String phoneNumber, String otp) {
        // Get OTP details by phone number
        OtpPhoneNumber otpReturned = otpService.getOtpByPhoneNumber(phoneNumber);

        // Validate OTP
        if (otpReturned != null&&otpReturned.isUsed()==false) {
            Instant creationTime = otpReturned.getCreationTimestamp();
            Instant expirationTime = creationTime.plus(Duration.ofMinutes(5));
            Instant currentTime = Instant.now();

            // Check if OTP is valid within the specified time frame
            if (otp.equals(otpReturned.getOtp()) && currentTime.isBefore(expirationTime)) {
                return true;
            }
        }

        return false;
    }
}

