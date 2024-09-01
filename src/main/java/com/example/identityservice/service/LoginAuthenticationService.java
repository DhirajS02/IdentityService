package com.example.identityservice.service;

import com.example.identityservice.model.AccessRefreshToken;
import com.example.identityservice.model.Employee;
import com.example.identityservice.model.RefreshToken;
import com.example.identityservice.repository.EmployeeRepository;
import com.example.identityservice.security.OtpAndPhoneNumberAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.example.identityservice.util.JwtUtil;

import java.time.Instant;
import java.time.ZoneId;

@Service
public class LoginAuthenticationService {

    @Value("${jwt.expireInMsRefreshToken}")
    private int expireInMsRefreshToken;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService otpAndPhoneNumberUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private EmployeeRepository employeeRepository;


    public AccessRefreshToken authenticateUsingOtp(String phoneNumber, String otp) throws AuthenticationException{
        /*Create Authentication Object to Authentication Manager to authenticate.Passed two parameters as
        authentication is set to false in 2 parameter constructor*/
        OtpAndPhoneNumberAuthenticationToken authenticationToken = new OtpAndPhoneNumberAuthenticationToken(phoneNumber, otp);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (authentication.isAuthenticated()) {
            //accessToken & Refresh Token is returned in form of String in AccessRefreshToken
            String accesstoken = jwtUtil.generateAccessToken(phoneNumber);
            String refreshtoken = jwtUtil.generateRefreshToken(phoneNumber);
            Employee employee=employeeRepository.findByMobileNumber(phoneNumber);
            AccessRefreshToken accessRefreshToken=new AccessRefreshToken(accesstoken,refreshtoken,employee);

            //We need to save refresh token in form of RefreshToken
           // RefreshToken refreshToken=new RefreshToken(autogeneratedId,refreshtoken,phoneNumber,expirationTime)
            //expirationTime=currentTime+expireInMsRefreshToken;
            ZoneId indianZone = ZoneId.of("Asia/Kolkata");
            Instant currentInstant = Instant.now();
            Instant indianTime = currentInstant.atZone(indianZone).toInstant();
            Instant expirationTime = indianTime.plusMillis(expireInMsRefreshToken);
            RefreshToken refreshToken = new RefreshToken(refreshtoken, phoneNumber, expirationTime);
            refreshTokenService.save(refreshToken);

            return accessRefreshToken;
        } else {
            throw new BadCredentialsException("Authentication failed.");
        }
    }
}
