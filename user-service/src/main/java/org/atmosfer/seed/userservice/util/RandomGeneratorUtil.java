package org.atmosfer.seed.userservice.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomGeneratorUtil {
    
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;
    
    public static String generateRandomPassword() {
        StringBuilder builder = new StringBuilder(PASSWORD_LENGTH);
        Random random = new SecureRandom();
        
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = random.nextInt(ALPHA_NUMERIC_STRING.length());
            char randomChar = ALPHA_NUMERIC_STRING.charAt(randomIndex);
            builder.append(randomChar);
        }
        
        return builder.toString();
    }

    public static String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}