package com.tswproject.tswproj;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Security {
    public static String getPasswordHash(String password) {
        String hashedPassword = "";

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            hashedPassword = sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException ignored) { }

        return hashedPassword;
    }

    public static boolean checkEmailFormat(String email) {
        return Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches();
    }

}
