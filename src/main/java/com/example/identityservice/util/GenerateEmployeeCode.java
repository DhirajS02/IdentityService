package com.example.identityservice.util;

import java.util.Random;

public class GenerateEmployeeCode {
    public static String generateEmployeeCode(int length) {
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder otpBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(digits.length());
            otpBuilder.append(digits.charAt(index));
        }

        return otpBuilder.toString();
    }

}
