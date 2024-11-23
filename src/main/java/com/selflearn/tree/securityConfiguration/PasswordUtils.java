package com.selflearn.tree.securityConfiguration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {

    private final static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encodedPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public static boolean isMatch(String password, String encoded) {
        return bCryptPasswordEncoder.matches(password, encoded);
    }

}
