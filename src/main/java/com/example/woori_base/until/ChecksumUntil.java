package com.example.woori_base.until;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ChecksumUntil {

    //tạo cặp khóa RSA để mã hóa
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

        //lấy một đối tượng signature để mã hóa bằng thuật toán sha256withrsa
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        //khởi tạo đối tượng privateSignature dùng privateKey để mã hóa
        privateSignature.initSign(privateKey);
        //cập nhật vào đối tượng privateSignature chuỗi checksum, và chuyển nó thành dạng byte
        privateSignature.update(checksum.getBytes(StandardCharsets.UTF_8));
        //kí dữ liệu
        byte[] signature = privateSignature.sign();
        //trả về dữ liệu đã mã hóa dạng base64
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

    //lưu khóa public vào file
    private static void savePublicKey(PublicKey publicKey, String fileName) throws IOException {
        byte[] publicKeyBytes = publicKey.getEncoded();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(Base64.getEncoder().encode(publicKeyBytes));
        fos.close();
    }

    //lưu khóa private vào file
    private static void savePrivateKey(PrivateKey privateKey, String fileName) throws IOException {
        byte[] privateKeyBytes = privateKey.getEncoded();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(Base64.getEncoder().encode(privateKeyBytes));
        fos.close();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        KeyPair keyPair = generateRSAKeyPair();
        savePublicKey(keyPair.getPublic(), "public-to-me.pem");
        savePrivateKey(keyPair.getPrivate(), "private-to-me.pem");

        savePublicKey(keyPair.getPublic(), "public-to-partner.pem");
        savePrivateKey(keyPair.getPrivate(), "private-to-partner.pem");
    }

    //hàm đọc khóa public trong file. khi file ở dạng .pem
    public static RSAPublicKey readX509PublicKey(String file) throws Exception {
        String key = Files.readString(Path.of(file), Charset.defaultCharset());
        byte[] encoded = Base64.getDecoder().decode(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    //hàm đọc khóa private .pem
    public static RSAPrivateKey readPKCS8PrivateKey(String file) throws Exception {
        String key = Files.readString(Path.of(file), Charset.defaultCharset());
        byte[] encoded = Base64.getDecoder().decode(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
}
