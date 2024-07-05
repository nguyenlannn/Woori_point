package com.example.woori_base.service.serviceimpl;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import com.example.woori_base.exception.BadRequestException;
import com.example.woori_base.service.UserService;
import com.example.woori_base.until.ApiCallUntil;
import com.example.woori_base.until.ChecksumUntil;
import com.example.woori_base.until.HeaderGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import static com.example.woori_base.WooriBaseApplication.generateRSAKeyPair;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private ApiCallUntil apiCallUntil;

    @Value("${api1.url}")
    private String apiLink;

    @Override
    public LinkRes postLink(LinkReq linkReq) {

        try {
            KeyPair keyPair = generateRSAKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            String reqChecksum = linkReq.getTmsDt() + linkReq.getTmsTm() + linkReq.getTrnSrno() + linkReq.getPrrstDscd() + linkReq.getActNo() + linkReq.getLinkType().toString() + linkReq.getTelNo() + linkReq.getCusNm() + linkReq.getCusIdNoCd() + linkReq.getCusIdNo();
            String encryptedChecksum=ChecksumUntil.encryptChecksum(reqChecksum, privateKey);
            //check chữ kí
            if(!ChecksumUntil.verifyChecksum(linkReq.getCheckSum(),encryptedChecksum, publicKey)) {
                throw new BadRequestException(8001, " Invalid signature", null);
            }
            String random = RandomStringUtils.random(8, "1234567890");//random một chuỗi có 8 kí tự số

            String msgDscd = "EW001";//mã mặc định
            String apCusNo = "123456";//mã đối tác
            HeaderGenerator headerGenerator = new HeaderGenerator();

            String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T271010VRequest seq=\"1\" device=\"TM\">" +
                    "<params>"
                    + "<msgTrno>" + random + "</msgTrno>"
                    + "<msgDscd>" + msgDscd + "</msgDscd>"
                    + "<apCusNo>" + apCusNo + "</msgTrno>"
                    + "<tmsDt>" + linkReq.getTmsDt() + "</tmsDt>"
                    + "<tmsTm>" + linkReq.getTmsTm() + "</tmsTm>"
                    + "<trnSrno>" + linkReq.getTrnSrno() + "</trnSrno>"
                    + "<prrstDscd>" + linkReq.getPrrstDscd() + "</prrstDscd>"
                    + "<actNo>" + linkReq.getActNo() + "</actNo>"
                    + "<linkType>" + linkReq.getLinkType().toString() + "</linkType>"
                    + "<telNo>" + linkReq.getTelNo() + "</telNo>"
                    + "<cusNm>" + linkReq.getCusNm() + "</cusNm>"
                    + "<cusIdNoCd>" + linkReq.getCusIdNoCd().toString() + "</cusIdNoCd>"
                    + "<cusIdNo>" + linkReq.getCusIdNo() + "</cusIdNo>"
                    + "</params></T271010VRequest></root>"));


            String apiResponse = apiCallUntil.makePostRequest(apiLink, mappingInput);
            //cắt chuỗi
            int start = apiResponse.indexOf("<params>");
            int end = apiResponse.indexOf("</params>");
            String subApiResponse = apiResponse.substring(start+"<params>".length(), end);

            JSONObject json = XML.toJSONObject(subApiResponse); // converts xml to json
            LinkRes linkRes = new LinkRes();
            linkRes.setTmsDt(json.get("tmsDt").toString());
            linkRes.setTmsTm(json.get("tmsTm").toString());
            linkRes.setTrnSrno(json.get("trnSrno").toString());
            linkRes.setRefNo(json.get("refNo").toString());
            linkRes.setRspCd(json.get("rspCd").toString());

            // nếu cột tokenId không có gì trả về, thì để defaultValue
            linkRes.setTokenId(json.optString("tokenId",""));
            linkRes.setErrEtc(json.get("errEtc").toString());

            String res=linkRes.getTmsDt()+linkRes.getTmsTm()+linkRes.getTrnSrno()+linkRes.getRefNo()+linkRes.getRspCd()+linkRes.getTokenId()+linkRes.getErrEtc();
            System.out.println(res);
            //gen ra checksum
            String genCheckSum = ChecksumUntil.encryptChecksum(res,privateKey);
            linkRes.setCheckSum(genCheckSum);
            return linkRes;

        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public VerifyLinkRes verifyLink(VerifyLinkReq verifyLinkReq) {
        //TODO
        return null;
    }

    @Override
    public UnlinkRes unlink(UnlinkReq unlinkReq) {
        //TODO
        return null;
    }

    @Override
    public RequestTopupRes requestTopup(RequestTopupReq requestTopupReq) {
        //TODO
        return null;
    }

    @Override
    public VerifyOtpRes verifyOtp(VerifyOtpReq verifyOtpReq) {
        return null;
    }

    @Override
    public TopupRes topUp(TopupReq topupReq) {
        return null;
    }

    @Override
    public CheckStatusRes checkStatus(CheckStatusReq checkStatusReq) {
        return null;
    }

    @Override
    public BalanceRes balance(BalanceReq balanceReq) {
        return null;
    }
}
