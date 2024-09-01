package com.example.identityservice.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;


@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "expiration_time")
    private Instant expirationTime;

    public Long getId() {
        return id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Instant expirationTime) {
        this.expirationTime = expirationTime;
    }

    public RefreshToken(String refreshToken, String phoneNumber, Instant expirationTime) {
        this.refreshToken = refreshToken;
        this.phoneNumber = phoneNumber;
        this.expirationTime = expirationTime;
    }

    public RefreshToken() {
    }

}
