package com;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileHasher {

    public static void main(String[] args) {
        String filePath = "logo.png"; // Replace with your file path

        try {
            String hash = getFileHash(filePath, "SHA-256");
            System.out.println("SHA-256 Hash: " + hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Requested cryptographic algorithm is not available.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public static String getFileHash(String filePath, String algorithm)
            throws NoSuchAlgorithmException, IOException {

        MessageDigest digest = MessageDigest.getInstance(algorithm);

        // Use a buffered stream to read large files efficiently without loading them entirely into memory
        try (InputStream is = Files.newInputStream(Paths.get(filePath))) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }

        // Convert the resulting byte array into a readable Hexadecimal string
        byte[] hashBytes = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}