package com.example.woori_base;

import com.example.woori_base.until.ChecksumUntil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

@SpringBootApplication
public class WooriBaseApplication {

	private ChecksumUntil checksumUntil;

	public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048);
		return keyGen.generateKeyPair();
	}

	// Tạo checksum từ chữ ký người dùng và thông tin request
	public static String generateChecksum(String userSignature, String requestData) {
		return userSignature + requestData;
	}

	// Mã hóa checksum bằng thuật toán SHA256WithRSA
	public static String encryptChecksum(String checksum, PrivateKey privateKey) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hashedData = digest.digest(checksum.getBytes(StandardCharsets.UTF_8));
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(privateKey);
		signature.update(hashedData);
		byte[] signedHash = signature.sign();
		return Base64.getEncoder().encodeToString(signedHash);
	}

	// Giải mã và xác minh checksum bằng thuật toán SHA256WithRSA
	public static boolean verifyChecksum(String checksum, String encryptedChecksum, PublicKey publicKey) throws Exception {
		Signature publicSignature = Signature.getInstance("SHA256withRSA");
		publicSignature.initVerify(publicKey);
		publicSignature.update(checksum.getBytes(StandardCharsets.UTF_8));

		byte[] signatureBytes = Base64.getDecoder().decode(encryptedChecksum);
		return publicSignature.verify(signatureBytes);
	}

	public static void main(String[] args) {
		SpringApplication.run(WooriBaseApplication.class, args);

		//mẫu cắt chuỗi
		String chuoi = "WGSS20240704110907307DMCEAPI01010068\n" +
				"0000076100000446WGSS20240704110907364DMCEAPI01010065000APIMCE01D                                                                                                                                             20240704110907364                 0087275001VQ100 NN00W5970001  SMCIG022                          00                                                                                                                            00                              0<root dataType=\"IN\"><275020VRequest  seq=\"1\" device=\"TM\"><params><msgTrno>BRAND1</msgTrno><msgDscd>EW008</msgDscd><apCusNo>MGV01</apCusNo><tmsDt>20230803</tmsDt><tmsTm>053104</tmsTm><trnSrno>20230625104623487295</trnSrno><prrstDscd>401</prrstDscd><actNo>100300005068</actNo></params></275020VRequest></root>\n";
		int start = chuoi.indexOf("<params>");
		int end = chuoi.indexOf("</params>");
		String chuoiCon = chuoi.substring(start+"<params>".length(), end);
		System.out.println(chuoiCon);

//  chuyển từ xml sang json bằng dependency json
//		try {
//			JSONObject json = XML.toJSONObject(chuoiCon); // converts xml to json
//			String jsonPrettyPrintString = json.toString(4); // json pretty print
//			System.out.println(jsonPrettyPrintString);
//
//		} catch(JSONException je) {
//			System.out.println(je.toString());
//		}
		try {
			// Tạo cặp khóa RSA
			KeyPair keyPair = generateRSAKeyPair();
			PrivateKey privateKey = keyPair.getPrivate();
			PublicKey publicKey = keyPair.getPublic();

			// Dữ liệu ví dụ
			String userSignature = "";
			String requestData = "";

			// Tạo checksum
			String checksum = generateChecksum(userSignature, requestData);

			String chuoiChecksum="a77278f3de168c7baefcc2ba3bb45bccedaf988a4f2c424c55d7e1eb8db3d903";
//
			// Mã hóa checksum
			String encryptedChecksum = encryptChecksum(checksum, privateKey);
			System.out.println("Checksum: " + checksum);
			System.out.println("Encrypted Checksum: " + encryptedChecksum);

			// Giải mã và xác minh checksum
			boolean isVerified = verifyChecksum(chuoiChecksum, encryptedChecksum, publicKey);
			System.out.println("Checksum verified: " + isVerified);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
