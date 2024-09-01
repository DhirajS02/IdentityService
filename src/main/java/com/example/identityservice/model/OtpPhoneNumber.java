package com.example.identityservice.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "otp_log")
public class OtpPhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "otp", nullable = false)
    private String otp;

    @Column(name = "creation_timestamp", nullable = false)
    private Instant creationTimestamp;

    @Column(name = "used", nullable = false)
    private boolean used;

    public OtpPhoneNumber() {
    }

    public OtpPhoneNumber(Long id, String phoneNumber, String otp, Instant creationTimestamp, boolean used) {
        this.phoneNumber = phoneNumber;
        this.otp = otp;
        this.creationTimestamp = creationTimestamp;
        this.used = used;
    }

    public Long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
