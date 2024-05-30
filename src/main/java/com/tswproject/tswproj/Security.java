package com.tswproject.tswproj;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Security {
    public static Optional<String> hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            return Optional.empty();
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = messageDigest.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return Optional.of(sb.toString());
        } catch (NoSuchAlgorithmException e) {
            return Optional.empty();
        }
    }

    public static boolean verifyPassword(String password, String hash) {
        if (hash == null) return false;
        Optional<String> hashed = hashPassword(password);
        return hashed.isPresent() && hashed.get().equals(hash);
    }

    public static boolean validateEmail(String email) {
        return Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches();
    }

    public static boolean validateUsername(String username) {
        // Username di lunghezza tra 3 e 20 caratteri di cui lettere maiuscole, minuscole numeri e ".", "-", "_"
        return Pattern.compile("^[a-zA-Z0-9._-]{3,20}$").matcher(username).matches();
    }
}
