package com.example.woori_base.service.serviceimpl;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import com.example.woori_base.exception.BusinessException;
import com.example.woori_base.exception.InternalException;
import com.example.woori_base.exception.ValidationException;
import com.example.woori_base.service.TopUpService;
import com.example.woori_base.until.ApiCallUntil;
import com.example.woori_base.until.ChecksumUntil;
import com.example.woori_base.until.HeaderGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class TopUpServiceImpl implements TopUpService {

    @Autowired
    private ApiCallUntil apiCallUntil;

    @Value("${api.urls.request-topup-e-wallet}")
    private String apiResquestToup;

    @Value("${api.urls.topup-confirm-e-wallet}")
    private String apiTopupConfirm;

    @Value("${api.urls.topup-under-1m-e-wallet}")
    private String apiTopupUnder;

    @Value("${api.urls.check-status}")
    private String apiCheckStatus;

    @Value("${api.urls.balance-inquiry}")
    private String apiBalanceInquiry;

    @Override
    public RequestTopupRes requestTopup(RequestTopupReq requestTopupReq) {
        RequestTopupRes res = new RequestTopupRes();

        String reqChecksum = requestTopupReq.getTmsDt() + requestTopupReq.getTmsTm() + requestTopupReq.getTrnSrno() + requestTopupReq.getPrrstDscd() + requestTopupReq.getTokenId() + requestTopupReq.getCurCd() + requestTopupReq.getEtcCtt();
        String encryptedChecksum = null;
        try {
            encryptedChecksum = ChecksumUntil.encryptChecksum(reqChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-partner.pem"));
        } catch (Exception ex) {
            throw new InternalException("8000", requestTopupReq.getTrnSrno(), "APIC system error");
        }
        boolean verifyChecksum = false;
        try {
            verifyChecksum = ChecksumUntil.verifyChecksum(reqChecksum, encryptedChecksum, ChecksumUntil.readX509PublicKey("public-to-partner.pem"));
        } catch (Exception e) {
            throw new InternalException("8000", requestTopupReq.getTrnSrno(), "APIC system error");
        }
        if (!verifyChecksum) {
            throw new ValidationException("8001", requestTopupReq.getTrnSrno(), "invalid signature");
        }
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "EW001";
        String apCusNo = "123456";
        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T271010VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<apCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + requestTopupReq.getTmsDt() + "</tmsDt>"
                + "<tmsTm>" + requestTopupReq.getTmsTm() + "</tmsTm>"
                + "<trnSrno>" + requestTopupReq.getTrnSrno() + "</trnSrno>"
                + "<prrstDscd>" + requestTopupReq.getPrrstDscd() + "</prrstDscd>"
                + "<tokenId>" + requestTopupReq.getTokenId() + "</tokenId>"
                + "<curCd>" + requestTopupReq.getCurCd() + "</curCd>"
                + "<etcCtt>" + requestTopupReq.getEtcCtt() + "</etcCtt>"
                + "</params></T271010VRequest></root>"));

        log.info("request core--------------------------");
        log.info(mappingInput);

        String apiResponse = apiCallUntil.makePostRequest(apiResquestToup, mappingInput);

        log.info("respon core--------------------------");
        log.info(apiResponse);

        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse); // converts xml to json
        res.setTmsDt(json.get("tmsDt").toString());
        res.setTmsTm(json.get("tmsTm").toString());
        res.setTrnSrno(json.get("trnSrno").toString());
        res.setRefNo(json.get("refNo").toString());
        res.setRspCd(json.get("rspCd").toString());

        String re = res.getTmsDt() + res.getTmsTm() + res.getTrnSrno() + res.getRspCd() + res.getRefNo();

        //gen ra checksum
        String genCheckSum = null;
        try {
            genCheckSum = ChecksumUntil.encryptChecksum(re, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem"));
        } catch (Exception e) {
            throw new BusinessException("8000", null, "APIC System Error");
        }
        res.setCheckSum(genCheckSum);

        log.info("respon core request topup--------------------------");
        log.info(res.toString());

        return res;
    }

    @Override
    public TopUpConfirmRes verifyOtp(TopUpConfirmReq req) {
        TopUpConfirmRes res = new TopUpConfirmRes();
        String reqChecksum = req.getTmsDt() + req.getTmsTm() + req.getTrnSrno() + req.getPrrstDscd() + req.getTokenId() + req.getTrnAmt() + req.getRefNo() + req.getOtpNo() + req.getCurCd() + req.getEtcCtt();
        String encryptedChecksum = null;
        try {
            encryptedChecksum = ChecksumUntil.encryptChecksum(reqChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-partner.pem"));
        } catch (Exception ex) {
            throw new BusinessException("8000", req.getTrnSrno(), "APIC System Error");
        }
        boolean verifyChecksum = false;
        try {
            verifyChecksum = ChecksumUntil.verifyChecksum(reqChecksum, encryptedChecksum, ChecksumUntil.readX509PublicKey("public-to-partner.pem"));
        } catch (Exception e) {
            throw new BusinessException("8000", null, "APIC System Error");
        }
        if (!verifyChecksum) {
            throw new ValidationException("8001", req.getTrnSrno(), "invalid signature");
        }
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "EW001";
        String apCusNo = "123456";
        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T271010VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<apCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + req.getTmsDt() + "</tmsDt>"
                + "<tmsTm>" + req.getTmsTm() + "</tmsTm>"
                + "<trnSrno>" + req.getTrnSrno() + "</trnSrno>"
                + "<prrstDscd>" + req.getPrrstDscd() + "</prrstDscd>"
                + "<tokenId>" + req.getTokenId() + "</tokenId>"
                + "<trnAmt>" + req.getTrnAmt() + "</trnAmt>"
                + "<refNo>" + req.getRefNo() + "</refNo>"
                + "<otpNo>" + req.getOtpNo() + "</otpNo>"
                + "<curCd>" + req.getCurCd() + "</curCd>"
                + "<etcCtt>" + req.getEtcCtt() + "</etcCtt>"
                + "</params></T271010VRequest></root>"));

        log.info("request core--------------------------");
        log.info(mappingInput);

        String apiResponse = apiCallUntil.makePostRequest(apiTopupConfirm, mappingInput);

        log.info("respon core--------------------------");
        log.info(apiResponse);

        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse);
        res.setTmsDt(json.get("tmsDt").toString());
        res.setTmsTm(json.get("tmsTm").toString());
        res.setTrnSrno(json.get("trnSrno").toString());
        res.setRefNo(json.get("refNo").toString());
        res.setRspCd(json.get("rspCd").toString());

        String ress = res.getTmsDt() + res.getTmsTm() + res.getTrnSrno() + res.getRefNo() + res.getRspCd();
        String genCheckSum = null;
        try {
            genCheckSum = ChecksumUntil.encryptChecksum(ress, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem"));
        } catch (Exception e) {
            throw new BusinessException("8000", null, "APIC System Error");
        }
        res.setCheckSum(genCheckSum);

        log.info("respon core api verify otp--------------------------");
        log.info(res.toString());

        return res;

    }

    @Override
    public TopupRes topUp(TopupReq topupReq) {
        TopupRes res = new TopupRes();
        String reqChecksum = topupReq.getTmsDt() + topupReq.getTmsTm() + topupReq.getTrnSrno() + topupReq.getPrrstDscd() + topupReq.getTokenId() + topupReq.getTrnAmt() + topupReq.getCurCd() + topupReq.getEtcCtt();
        String encryptedChecksum = null;
        try {
            encryptedChecksum = ChecksumUntil.encryptChecksum(reqChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-partner.pem"));
        } catch (Exception ex) {
            throw new BusinessException("8000", topupReq.getTrnSrno(), "APIC System Error");
        }
        boolean verifyChecksum = false;
        try {
            verifyChecksum = ChecksumUntil.verifyChecksum(reqChecksum, encryptedChecksum, ChecksumUntil.readX509PublicKey("public-to-partner.pem"));
        } catch (Exception e) {
            throw new BusinessException("8000", null, "APIC System Error");
        }
        if (!verifyChecksum) {
            throw new ValidationException("8001", topupReq.getTrnSrno(), "invalid signature");
        }
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "EW001";
        String apCusNo = "123456";
        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T271010VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<apCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + topupReq.getTmsDt() + "</tmsDt>"
                + "<tmsTm>" + topupReq.getTmsTm() + "</tmsTm>"
                + "<trnSrno>" + topupReq.getTrnSrno() + "</trnSrno>"
                + "<prrstDscd>" + topupReq.getPrrstDscd() + "</prrstDscd>"
                + "<tokenId>" + topupReq.getTokenId() + "</tokenId>"
                + "<trnAmt>" + topupReq.getTrnAmt() + "</trnAmt>"
                + "<curCd>" + topupReq.getCurCd() + "</curCd>"
                + "<etcCtt>" + topupReq.getEtcCtt() + "</etcCtt>"
                + "</params></T271010VRequest></root>"));

        log.info("request core--------------------------");
        log.info(mappingInput);

        String apiResponse = apiCallUntil.makePostRequest(apiTopupUnder, mappingInput);

        log.info("respon core--------------------------");
        log.info(apiResponse);

        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse);
        res.setTmsDt(json.get("tmsDt").toString());
        res.setTmsTm(json.get("tmsTm").toString());
        res.setTrnSrno(json.get("trnSrno").toString());
        res.setRspCd(json.get("rspCd").toString());

        String ress = res.getTmsDt() + res.getTmsTm() + res.getTrnSrno() + res.getRspCd();
        String genCheckSum = null;
        try {
            genCheckSum = ChecksumUntil.encryptChecksum(ress, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem"));
        } catch (Exception e) {
            throw new BusinessException("8000", null, "APIC System Error");
        }
        res.setChecksum(genCheckSum);

        log.info("respon core api topup--------------------------");
        log.info(res.toString());

        return res;
    }

    @Override
    public CheckStatusRes checkStatus(CheckStatusReq statusReq) {
        CheckStatusRes res = new CheckStatusRes();
        String reqChecksum = statusReq.getTmsDt() + statusReq.getTmsTm() + statusReq.getTrnSrno() + statusReq.getPrrstDscd() + statusReq.getOrgTrnSrno() + statusReq.getOrgTmsDt();
        String encryptedChecksum = null;
        try {
            encryptedChecksum = ChecksumUntil.encryptChecksum(reqChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-partner.pem"));
        } catch (Exception ex) {
            throw new BusinessException("8000", statusReq.getTrnSrno(), "APIC System Error");
        }
        boolean verifyChecksum = false;
        try {
            verifyChecksum = ChecksumUntil.verifyChecksum(reqChecksum, encryptedChecksum, ChecksumUntil.readX509PublicKey("public-to-partner.pem"));
        } catch (Exception e) {
            throw new BusinessException("8000", null, "APIC System Error");
        }
        if (!verifyChecksum) {
            throw new ValidationException("8001", statusReq.getTrnSrno(), "invalid signature");
        }
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "EW001";
        String apCusNo = "123456";
        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T271010VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<apCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + statusReq.getTmsDt() + "</tmsDt>"
                + "<tmsTm>" + statusReq.getTmsTm() + "</tmsTm>"
                + "<trnSrno>" + statusReq.getTrnSrno() + "</trnSrno>"
                + "<prrstDscd>" + statusReq.getPrrstDscd() + "</prrstDscd>"
                + "<orgTrnSrno>" + statusReq.getOrgTrnSrno() + "</orgTrnSrno>"
                + "<orgTmsDt>" + statusReq.getOrgTmsDt() + "</orgTmsDt>"
                + "</params></T271010VRequest></root>"));

        log.info("request core--------------------------");
        log.info(mappingInput);

        String apiResponse = apiCallUntil.makePostRequest(apiCheckStatus, mappingInput);

        log.info("respon core--------------------------");
        log.info(apiResponse);

        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse);
        res.setTmsDt(json.get("tmsDt").toString());
        res.setTmsTm(json.get("tmsTm").toString());
        res.setTrnSrno(json.get("trnSrno").toString());
        //todo nêú có thì trả về, ko thì thôi
        res.setOrgStsDscd(json.optString("orgStsDscd",""));
        res.setRspCd(json.get("rspCd").toString());

        String ress = res.getTmsDt() + res.getTmsTm() + res.getTrnSrno() + res.getOrgStsDscd() + res.getRspCd();
        String genCheckSum = null;
        try {
            genCheckSum = ChecksumUntil.encryptChecksum(ress, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem"));
        } catch (Exception e) {
            throw new BusinessException("8000", null, "APIC System Error");
        }
        res.setChecksum(genCheckSum);

        log.info("respon core api check status--------------------------");
        log.info(res.toString());

        return res;
    }

    @Override
    public BalanceRes balance(BalanceReq balanceReq) {
        BalanceRes res = new BalanceRes();
        String reqChecksum = balanceReq.getTmsDt() + balanceReq.getTmsTm() + balanceReq.getTrnSrno() + balanceReq.getPrrstDscd() + balanceReq.getActNo();
        String encryptedChecksum = null;
        try {
            encryptedChecksum = ChecksumUntil.encryptChecksum(reqChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-partner.pem"));
        } catch (Exception ex) {
            throw new BusinessException("8000", balanceReq.getTrnSrno(), "APIC System Error");
        }
        boolean verifyChecksum = false;
        try {
            verifyChecksum = ChecksumUntil.verifyChecksum(reqChecksum, encryptedChecksum, ChecksumUntil.readX509PublicKey("public-to-partner.pem"));
        } catch (Exception e) {
            throw new BusinessException("8000", null, "APIC System Error");
        }
        if (!verifyChecksum) {
            throw new ValidationException("8001", balanceReq.getTrnSrno(), "invalid signature");
        }
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "EW001";
        String apCusNo = "123456";
        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T271010VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<apCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + balanceReq.getTmsDt() + "</tmsDt>"
                + "<tmsTm>" + balanceReq.getTmsTm() + "</tmsTm>"
                + "<trnSrno>" + balanceReq.getTrnSrno() + "</trnSrno>"
                + "<prrstDscd>" + balanceReq.getPrrstDscd() + "</prrstDscd>"
                + "<actNo>" + balanceReq.getActNo() + "</actNo>"
                + "</params></T271010VRequest></root>"));

        log.info("request core--------------------------");
        log.info(mappingInput);

        String apiResponse = apiCallUntil.makePostRequest(apiBalanceInquiry, mappingInput);

        log.info("respon core--------------------------");
        log.info(apiResponse);

        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse);
        res.setTmsDt(json.get("tmsDt").toString());
        res.setTmsTm(json.get("tmsTm").toString());
        res.setTrnSrno(json.get("trnSrno").toString());
        res.setTrnAmt((Integer) json.get("trnAmt"));
        res.setRspCd(json.get("rspCd").toString());

        String ress = res.getTmsDt() + res.getTmsTm() + res.getTrnSrno() + res.getTrnAmt() + res.getRspCd();
        String genCheckSum = null;
        try {
            genCheckSum = ChecksumUntil.encryptChecksum(ress, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem"));
        } catch (Exception e) {
            throw new BusinessException("8000", null, "APIC System Error");
        }
        res.setChecksum(genCheckSum);

        log.info("respon core api balance--------------------------");
        log.info(res.toString());

        return res;
    }
}
