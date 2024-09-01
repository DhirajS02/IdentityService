package com.example.identityservice.service;

import com.example.identityservice.model.OtpPhoneNumber;
import com.example.identityservice.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;

@Service
public class OtpService {
    @Autowired
    OtpRepository otpRepository;

    public void saveOtp(String phNumber,String otp)
    {
        OtpPhoneNumber otpPhoneNumber = new OtpPhoneNumber();
        otpPhoneNumber.setOtp(otp);
        otpPhoneNumber.setPhoneNumber(phNumber);

        ZoneId indianZone = ZoneId.of("Asia/Kolkata");
        Instant currentInstant = Instant.now();
        Instant indianTime = currentInstant.atZone(indianZone).toInstant();
        otpPhoneNumber.setCreationTimestamp(indianTime);

        otpPhoneNumber.setUsed(false);

        otpRepository.save(otpPhoneNumber);
    }

    public OtpPhoneNumber getOtpByPhoneNumber(String phoneNumber)
    {
        return otpRepository.findByPhoneNumber(phoneNumber);
    }

    public void deleteByPhoneNumber(String phoneNumber)
    {
        otpRepository.deleteByPhoneNumber(phoneNumber);
    }

    public int markOtpAsUsedByPhoneNumber(String phoneNumber)
    {
        return otpRepository.markOtpAsUsedByPhoneNumber(phoneNumber);
    }



}
