package com.eauction.application.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

@Slf4j
public class Utils {

    private static String encryptCode = "2f632e6426b84be2800e4b6fb31dc5f3";

    public static String decryptAES(String encryptedPassword) {
        String decryptedPassword = "";
        try {
            String sessionSha256 = Objects.requireNonNull(calculateSHA256(encryptCode)).substring(0, 32);
            SecretKeySpec secretKeySpec = new SecretKeySpec(sessionSha256.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); //NOSONAR
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
            decryptedPassword = new String(cipher.doFinal(decodedBytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Error while decrypting: " + e);
        }
        return decryptedPassword;
    }

    public static String calculateSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            String ret = byteToHex(hashBytes);
            return ret;
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA-256 algorithm not available: " + e.getMessage());
            return null;
        }
    }

    private static String byteToHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
