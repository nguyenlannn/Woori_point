package com.example.woori_base;

import com.example.woori_base.until.ChecksumUntil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WooriBaseApplication {

	private ChecksumUntil checksumUntil;

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
	}
}
