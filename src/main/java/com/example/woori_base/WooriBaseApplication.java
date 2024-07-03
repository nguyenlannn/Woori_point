package com.example.woori_base;

import com.example.woori_base.until.HeaderGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WooriBaseApplication {

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(WooriBaseApplication.class, args);


		String chuoi="0000083700000446WGSS20230719134329961DMCEAPI01010090000APIMCE01D202307191343299610087275001VP100 NN00W5970001SMCIG02200000<root dataType=\"OUT\"><T275001VResponse seq=\"1\" device=\"TM\" scrNo=\"121101M\" emitYn=\"N\"><params><msgTrno>00000623</msgTrno><msgDscd>EW001</msgDscd><apCusNo>VNI001</apCusNo><tmsDt>20230719</tmsDt><tmsTm>134329</tmsTm><trnSrno>20230625104623487290</trnSrno><refNo>26311762487055401717</refNo><rspCd>0000</rspCd><errEtc>Process is completed</errEtc></params></T275001VResponse></root>";
		int start = chuoi.indexOf("<params>");
		int end = chuoi.indexOf("</T275001VResponse>");
		String chuoiCon = chuoi.substring(start,end);
		System.out.println(chuoiCon);


//  chuyển từ xml sang json bằng dependency json
		try {
			JSONObject json = XML.toJSONObject(chuoiCon); // converts xml to json
			String jsonPrettyPrintString = json.toString(4); // json pretty print
			System.out.println(jsonPrettyPrintString);

		} catch(JSONException je) {
			System.out.println(je.toString());
		}
	}
}
