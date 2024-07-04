package com.example.woori_base.until;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class ChecksumUntil {

    //tạo cặp khóa RSA để mã khóa
    public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    //nối chuỗi chữ kí và request đầu vào
    public static String generateChecksum(String signature, String requestData) {
        return signature + requestData;
    }

    //thực hiện mã hóa
    public static String encryptChecksum(String checksum, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(checksum.getBytes(StandardCharsets.UTF_8));

        byte[] signature = privateSignature.sign();
        return Base64.getEncoder().encodeToString(signature);
    }
    //giải mã và verify chữ kí của mã checksum
    public static boolean verifyChecksum(String checksum, String encryptedChecksum, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(checksum.getBytes(StandardCharsets.UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(encryptedChecksum);
        return publicSignature.verify(signatureBytes);
    }
}
