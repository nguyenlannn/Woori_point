package com.example.woori_base.until;//package com.example.woori_base.until;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TestEncypt {

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

    public static void main(String[] args) throws Exception {

        String plaintext="{\"requestId\":\"19869142769202493749176622387887615\",\"membershipId\":\"26650919\",\"mobilePhoneNo\":\"0982120014\",\"dateOfBirth\":\"1986-11-10\",\"gender\":\"M\",\"customerName\":\"NGUYENHONGSONA\",\"emailAddress\":\"sonhn86@gmail.com\",\"vanilaBarcode\":\"4967884915604141\",\"addressDetailInfo\":{\"state\":\"123ashd\",\"district\":\"238dfj\",\"detail1\":\"null\",\"detail2\":\"null\"},\"countryCode\":\"VN\",\"idType\":\"CCCD\",\"idNumber\":\"012385454555\"}";
        String iv="EIxcti+4OUPSYH4L";
        String key = "hehebatngochuacainaylakeydo12345";

        String cipher= encrypt(plaintext,iv,key);
        System.out.println("Encrypted Data: " + cipher);
    }
}