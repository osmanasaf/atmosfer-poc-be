package org.codefirst.seed.userservice.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptUtil {
    private static final BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();

    public static String encode(CharSequence rawPassword) {
        return "{CRYPT}" + crypt.encode(rawPassword);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return crypt.matches(rawPassword, encodedPassword.substring(7));
    }
}
