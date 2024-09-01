package com.example.identityservice.model;

public class AccessRefreshToken {

    private String accessToken;
    private String refreshToken;
    private Employee employee;

    public AccessRefreshToken(String accessToken, String refreshToken, Employee employee) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.employee = employee;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
