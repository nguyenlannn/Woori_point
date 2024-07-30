package com.example.woori_base.until;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class TestDecypt {
    public static String decryptSymmetric128BitHexKeyUTF8(String ciphertext, String iv, String tag, String key) throws Exception {
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

    public static void main(String[] args) throws Exception {
        String ciphertext = "R2t77NAWLNJhgPYUdxy+Pw/HvyyDs1M9G2IAyqh9GpPLTnXz+l6FKdijLURZ8NRIVHh0ZioGiURivFVN9N9iPx6yJCUZLvUBETrGrH5DY2ic37S3UMPEedgHkpvbPbkVM+VLO+envuhllMCekNpF7iDaClsu6tdoNdJ5xpDEexFPuR9fvCIZSbRiS3KDFPcu1m/YH+quTsxRKkUTP0f4IBJgsJ6+Zcd4BCq+0DCI4z3nXgin21tsIvJOrnWIVP5O7KTmmhbSHp4vwjFutbeClLptMgAQfcPXtUX4XYWuA/2pYVh1mwbgrJvcNff6xNS9YYTVRdZUaYyVpB7/cmqs8CJmhbXdoCbpMKMTZNrVwSklmntwC9t72wOHyR8NcHXluF5Jm6UaJN1lobAFl1B2tzwzAsbO5BSs3ytwHGNJZNwBMKCBn+3juEQRHTshbkr8N0G2TmkdldL3eLCS/vQ7bx030kFKO5moaeWaowXvUqcxKobH7jZF1dw2jTZQK1h4Ru5d3G0RwdeHRkQHnVZRD2MJhA==";
        String iv = "EIxcti+4OUPSYH4L";
        String tag = "jQNIVXxy0R4lcFaBftL8ng==";
        String key = "hehebatngochuacainaylakeydo12345";

        String decryptedText = decryptSymmetric128BitHexKeyUTF8(ciphertext, iv, tag, key);
        System.out.println("------ chuỗi ban đầu: " + decryptedText);
    }
}
