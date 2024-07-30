package com.example.woori_base.until;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CipherIvUntil {

    //mã hoá
    public static String encrypt(String plaintext, String iv, String key) throws Exception {
        String algorithm = "AES/GCM/NoPadding";

        // giải mã các chuỗi truyền vào
        byte[] ivBytes = Base64.getDecoder().decode(iv);
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);

        Cipher cipher = Cipher.getInstance(algorithm);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);

        byte[] encryptedBytes = cipher.doFinal(plaintextBytes);

        //
        int tagLength = 16;
        byte[] ciphertextBytes = new byte[encryptedBytes.length - tagLength];
        byte[] tagBytes = new byte[tagLength];
        System.arraycopy(encryptedBytes, 0, ciphertextBytes, 0, ciphertextBytes.length);
        System.arraycopy(encryptedBytes, encryptedBytes.length - tagLength, tagBytes, 0, tagLength);

        // mã hoá dể trả về dữ liệu-base64
        String ciphertextBase64 = Base64.getEncoder().encodeToString(ciphertextBytes);
        String tagBase64 = Base64.getEncoder().encodeToString(tagBytes);

        return ciphertextBase64 + ":" + tagBase64;
    }

    //giai mã
    public static String decrypt(String ciphertext, String iv, String tag, String key) throws Exception {
        String algorithm = "AES/GCM/NoPadding";

        byte[] ciphertextBytes = Base64.getDecoder().decode(ciphertext);
        byte[] ivBytes = Base64.getDecoder().decode(iv);
        byte[] tagBytes = Base64.getDecoder().decode(tag);
        byte[] keyBytes = key.getBytes("UTF-8");

        // Fix: concatenate ciphertext and tag
        byte[] ciphertextTagBytes = new byte[ciphertextBytes.length + tagBytes.length];
        System.arraycopy(ciphertextBytes, 0, ciphertextTagBytes, 0, ciphertextBytes.length);
        System.arraycopy(tagBytes, 0, ciphertextTagBytes, ciphertextBytes.length, tagBytes.length);

        Cipher cipher = Cipher.getInstance(algorithm);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);

        byte[] decrypted = cipher.doFinal(ciphertextTagBytes);
        return new String(decrypted, "UTF-8");
    }
}
