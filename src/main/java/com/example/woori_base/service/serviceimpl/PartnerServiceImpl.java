package com.example.woori_base.service.serviceimpl;

import com.example.woori_base.dto.req.CreateChecksumReq;
import com.example.woori_base.dto.req.LinkReq;
import com.example.woori_base.dto.req.UnlinkReq;
import com.example.woori_base.dto.req.VerifyLinkReq;
import com.example.woori_base.dto.res.LinkRes;
import com.example.woori_base.dto.res.UnlinkRes;
import com.example.woori_base.dto.res.VerifyLinkRes;
import com.example.woori_base.exception.BusinessException;
import com.example.woori_base.exception.InternalException;
import com.example.woori_base.exception.ValidationException;
import com.example.woori_base.service.PartnerService;
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

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Log4j2
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private ApiCallUntil apiCallUntil;

    @Value("${api.urls.link-e-wallet}")
    private String apiLink;

    @Value("${api.urls.link-e-wallet-confirm}")
    private String apiLinkConfirm;

    @Value("${api.urls.unlink-e-wallet}")
    private String apiUnLink;

    @Override
    public String createChecksum(CreateChecksumReq req) {
        String InputChecksum = req.getTmsDt() + req.getTmsTm() + req.getTrnSrno() + req.getPrrstDscd() + req.getActNo() + req.getLinkType() + req.getTelNo() + req.getCusNm() + req.getCusIdNoCd() + req.getCusIdNo();
        String checksum = null;
        try {

            checksum = ChecksumUntil.encryptChecksum(InputChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-partner.pem"));
        } catch (Exception e) {
            throw new InternalException("",null,"An error occurred in the system");
        }
        return checksum;
    }

    @Override
    public LinkRes postLink(LinkReq linkReq) {
        LinkRes linkRes = new LinkRes();
        // nối chuỗi resquest đầu vào
        String reqChecksum = linkReq.getTmsDt() + linkReq.getTmsTm() + linkReq.getTrnSrno() + linkReq.getPrrstDscd() + linkReq.getActNo() + linkReq.getLinkType().toString() + linkReq.getTelNo() + linkReq.getCusNm() + linkReq.getCusIdNoCd() + linkReq.getCusIdNo();
        // tạo chuỗi checksum từ request đầu vào
        String encryptedChecksum = null;
        try {
            encryptedChecksum = ChecksumUntil.encryptChecksum(reqChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-partner.pem"));
        } catch (Exception ex) {
            throw new InternalException("8000",linkReq.getTrnSrno(),"APIC system error");
        }
        //check chữ kí
        boolean verifyChecksum = false;
        try {
            verifyChecksum = ChecksumUntil.verifyChecksum(reqChecksum, encryptedChecksum, ChecksumUntil.readX509PublicKey("public-to-partner.pem"));
        } catch (Exception e) {
            throw new InternalException("8000", linkReq.getTrnSrno(),"APIC system error");
        }
        if (!verifyChecksum) {
            throw new ValidationException("8001", linkReq.getTrnSrno(), "invalid signature");
        }
        //random một chuỗi có 8 kí tự số
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "EW001";//mã mặc định
        String apCusNo = "123456";//mã đối tác
        //mapping resquest đầu vào thành kiểu xml
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

        // chuyển chuỗi resquest đã mã hóa sang một hệ thống khác
        String apiResponse = null;
        try {
            apiResponse = apiCallUntil.makePostRequest(apiLink, mappingInput);
        } catch (IOException ex) {
            throw new BusinessException("8000",null,"APIC System Error");
        }
        //cắt chuỗi trả về
        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        // chuyển thành dạng json để trả về
        JSONObject json = XML.toJSONObject(subApiResponse); // converts xml to json
        linkRes.setTmsDt(json.get("tmsDt").toString());
        linkRes.setTmsTm(json.get("tmsTm").toString());
        linkRes.setTrnSrno(json.get("trnSrno").toString());
        linkRes.setRefNo(json.get("refNo").toString());
        linkRes.setRspCd(json.get("rspCd").toString());
        // nếu cột tokenId không có gì trả về, thì để defaultValue
        linkRes.setTokenId(json.optString("tokenId", ""));
        linkRes.setErrEtc(json.get("errEtc").toString());

        String res = linkRes.getTmsDt() + linkRes.getTmsTm() + linkRes.getTrnSrno() + linkRes.getRefNo() + linkRes.getRspCd() + linkRes.getTokenId() + linkRes.getErrEtc();
        System.out.println(res);
        //gen ra checksum
        String genCheckSum = null;
        try {
            genCheckSum = ChecksumUntil.encryptChecksum(res, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem"));
        } catch (Exception e) {
            throw new InternalException("8000",linkReq.getTrnSrno(),"APIC system error");
        }
        linkRes.setCheckSum(genCheckSum);
        return linkRes;
    }

    @Override
    public VerifyLinkRes verifyLink(VerifyLinkReq verifyLinkReq) {
        VerifyLinkRes res = new VerifyLinkRes();
        // nối chuỗi resquest đầu vào
        String reqChecksum = verifyLinkReq.getTmsDt() + verifyLinkReq.getTmsTm() + verifyLinkReq.getTrnSrno() + verifyLinkReq.getPrrstDscd() + verifyLinkReq.getRefNo() + verifyLinkReq.getOtpNo();
        // tạo chuỗi checksum từ request đầu vào
        String encryptedChecksum = null;
        try {
            encryptedChecksum = ChecksumUntil.encryptChecksum(reqChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-partner.pem"));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        //check chữ kí
        boolean verifyChecksum = false;
        try {
            verifyChecksum = ChecksumUntil.verifyChecksum(reqChecksum, encryptedChecksum, ChecksumUntil.readX509PublicKey("public-to-partner.pem"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!verifyChecksum) {
            throw new ValidationException("8001", verifyLinkReq.getTrnSrno(), "invalid signature");
        }
        //random một chuỗi có 8 kí tự số
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "EW001";//mã mặc định
        String apCusNo = "123456";//mã đối tác
        //mapping resquest đầu vào thành kiểu xml
        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T271010VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<apCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + verifyLinkReq.getTmsDt() + "</tmsDt>"
                + "<tmsTm>" + verifyLinkReq.getTmsTm() + "</tmsTm>"
                + "<trnSrno>" + verifyLinkReq.getTrnSrno() + "</trnSrno>"
                + "<prrstDscd>" + verifyLinkReq.getPrrstDscd() + "</prrstDscd>"
                + "<refNo>" + verifyLinkReq.getRefNo() + "</refNo>"
                + "<otpNo>" + verifyLinkReq.getOtpNo() + "</otpNo>"
                + "</params></T271010VRequest></root>"));

        // chuyển chuỗi resquest đã mã hóa sang một hệ thống khác
        String apiResponse = null;
        try {
            apiResponse = apiCallUntil.makePostRequest(apiLink, mappingInput);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //cắt chuỗi trả về
        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        // chuyển thành dạng json để trả về
        JSONObject json = XML.toJSONObject(subApiResponse); // converts xml to json
        res.setTmsDt(json.get("tmsDt").toString());
        res.setTmsTm(json.get("tmsTm").toString());
        res.setTrnSrno(json.get("trnSrno").toString());
        res.setRspCd(json.get("rspCd").toString());
        // nếu cột tokenId không có gì trả về, thì để defaultValue
        res.setTokenId(json.optString("tokenId", ""));
        res.setErrEtc(json.get("errEtc").toString());

        String ress = res.getTmsDt() + res.getTmsTm() + res.getTrnSrno() + res.getRspCd() + res.getTokenId() + res.getErrEtc();

        //gen ra checksum
        String genCheckSum = null;
        try {
            genCheckSum = ChecksumUntil.encryptChecksum(ress, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        res.setCheckSum(genCheckSum);
        return res;
    }

    @Override
    public UnlinkRes unlink(UnlinkReq unlinkReq) {
        UnlinkRes unlinkRes = new UnlinkRes();
        // nối chuỗi resquest đầu vào
        String reqChecksum = unlinkReq.getTmsDt() + unlinkReq.getTmsTm() + unlinkReq.getTrnSrno() + unlinkReq.getPrrstDscd() + unlinkReq.getTokenId() + unlinkReq.getTelNo();
        // tạo chuỗi checksum từ request đầu vào
        String encryptedChecksum = null;
        try {
            encryptedChecksum = ChecksumUntil.encryptChecksum(reqChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-partner.pem"));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        //check chữ kí
        boolean verifyChecksum = false;
        try {
            verifyChecksum = ChecksumUntil.verifyChecksum(reqChecksum, encryptedChecksum, ChecksumUntil.readX509PublicKey("public-to-partner.pem"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!verifyChecksum) {
            throw new ValidationException("8001", unlinkReq.getTrnSrno(), "invalid signature");
        }
        //random một chuỗi có 8 kí tự số
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "EW001";//mã mặc định
        String apCusNo = "123456";//mã đối tác
        //mapping resquest đầu vào thành kiểu xml
        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T271010VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<apCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + unlinkReq.getTmsDt() + "</tmsDt>"
                + "<tmsTm>" + unlinkReq.getTmsTm() + "</tmsTm>"
                + "<trnSrno>" + unlinkReq.getTrnSrno() + "</trnSrno>"
                + "<prrstDscd>" + unlinkReq.getPrrstDscd() + "</prrstDscd>"
                + "<tokenId>" + unlinkReq.getTokenId() + "</tokenId>"
                + "<telNo>" + unlinkReq.getTelNo() + "</telNo>"
                + "</params></T271010VRequest></root>"));

        // chuyển chuỗi resquest đã mã hóa sang một hệ thống khác
        String apiResponse = null;
        try {
            apiResponse = apiCallUntil.makePostRequest(apiLink, mappingInput);
        } catch (IOException ex) {
            throw new BusinessException("8000",null,"APIC System Error");
        }
        //cắt chuỗi trả về
        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        // chuyển thành dạng json để trả về
        JSONObject json = XML.toJSONObject(subApiResponse); // converts xml to json
        unlinkRes.setTmsDt(json.get("tmsDt").toString());
        unlinkRes.setTmsTm(json.get("tmsTm").toString());
        unlinkRes.setTrnSrno(json.get("trnSrno").toString());
        unlinkRes.setRspCd(json.get("rspCd").toString());
        unlinkRes.setErrEtc(json.get("errEtc").toString());

        String ress = unlinkRes.getTmsDt() + unlinkRes.getTmsTm() + unlinkRes.getTrnSrno() + unlinkRes.getRspCd() + unlinkRes.getErrEtc();

        //gen ra checksum
        String genCheckSum = null;
        try {
            genCheckSum = ChecksumUntil.encryptChecksum(ress, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        unlinkRes.setCheckSum(genCheckSum);
        return unlinkRes;
    }
}
