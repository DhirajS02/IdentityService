package com.example.identityservice.service;

import com.example.identityservice.customexceptions.InvalidJwtToken;
import com.example.identityservice.customexceptions.InvalidRefreshToken;
import com.example.identityservice.customexceptions.RefreshTokenDoesNotExist;
import com.example.identityservice.model.RefreshToken;
import com.example.identityservice.repository.RefreshTokenRepository;
import com.example.identityservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RefreshTokenService {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    public String validateAndReturnAccessToken(String refreshToken) throws InvalidRefreshToken {

        String accessToken="";
        try {
            if (jwtUtil.getUsername(refreshToken) != null) {
                String username = jwtUtil.getUsername(refreshToken);
                RefreshToken refreshTokenFromDatabase = refreshTokenRepository.findByRefreshToken(refreshToken);
                if(refreshTokenFromDatabase==null)
                {
                    throw new RefreshTokenDoesNotExist("Does not exist");
                }
                Instant expirationTime = refreshTokenFromDatabase.getExpirationTime();

                if (expirationTime.isAfter(Instant.now()) && username.equals(refreshTokenFromDatabase.getPhoneNumber())) {
                    accessToken = jwtUtil.generateAccessToken(username);
                    return accessToken;
                }
            }
            else
            {
                throw new InvalidJwtToken("Invalid Refresh Token. Refresh Token is not valid or expired");
            }
        } catch (RefreshTokenDoesNotExist e) {
            throw new InvalidJwtToken("Invalid Refresh Token");
        }
        catch (Exception e) {
            throw new InvalidJwtToken("Invalid Refresh Token");
        }
        return accessToken;
    }

    public void save(RefreshToken refreshToken){
        refreshTokenRepository.save(refreshToken);
    }


}
