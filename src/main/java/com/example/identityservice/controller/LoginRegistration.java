package com.example.identityservice.controller;

import com.example.identityservice.customexceptions.InvalidRefreshToken;
import com.example.identityservice.model.AccessRefreshToken;
import com.example.identityservice.model.ApiResponse;
import com.example.identityservice.model.Employee;
import com.example.identityservice.service.EmployeeService;
import com.example.identityservice.service.LoginAuthenticationService;
import com.example.identityservice.service.OtpService;
import com.example.identityservice.service.RefreshTokenService;
import com.example.identityservice.util.GenerateOtp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/identityservice")
public class LoginRegistration {

    private final LoginAuthenticationService loginAuthenticationService;

    private final RefreshTokenService refreshTokenService;

    private final OtpService otpService;

    private final EmployeeService employeeService;

    @Autowired
    public LoginRegistration(LoginAuthenticationService loginAuthenticationService, RefreshTokenService refreshTokenService, OtpService otpService, EmployeeService employeeService) {
        this.loginAuthenticationService = loginAuthenticationService;
        this.refreshTokenService = refreshTokenService;
        this.otpService = otpService;
        this.employeeService = employeeService;
    }

    @PostMapping("/otp")
    public ResponseEntity<ApiResponse<String>> generateOtp(@RequestParam("phoneNumber") String phoneNumber) {
        //TODO
        // add twilio
        String otp= GenerateOtp.generateOTP(4);
        //TODO
        // only to check which otp is generated
        System.out.println("otp = " + otp);
        if(otpService.getOtpByPhoneNumber(phoneNumber)!=null)
        {
          otpService.deleteByPhoneNumber(phoneNumber);
        }
        otpService.saveOtp(phoneNumber,otp);
        //
        return new ResponseEntity<ApiResponse<String>>(ApiResponse.success("Otp Message sent"),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccessRefreshToken>> login(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("otp") String otp) {
            AccessRefreshToken token = loginAuthenticationService.authenticateUsingOtp(phoneNumber, otp);
            return new ResponseEntity<ApiResponse<AccessRefreshToken>>(ApiResponse.success(token),HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> getAccessToken(@RequestParam("refreshToken") String refreshToken) throws InvalidRefreshToken {
        String accessToken = refreshTokenService.validateAndReturnAccessToken(refreshToken);
        return new ResponseEntity<ApiResponse<String>>(ApiResponse.success(accessToken),HttpStatus.OK);

    }

    @PostMapping(value="/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<Employee> register(@RequestBody Employee employee) {

        return new ResponseEntity<Employee>(employee,HttpStatus.OK);
    }

    @PostMapping(value="/staff",consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) throws Exception{
        Employee savedEmployees = employeeService.save(employee);
        return new ResponseEntity<>(savedEmployees, HttpStatus.CREATED);
    }
}
