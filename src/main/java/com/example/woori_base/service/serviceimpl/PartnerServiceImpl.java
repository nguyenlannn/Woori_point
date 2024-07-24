package com.example.woori_base.service.serviceimpl;

import com.example.woori_base.base.BaseRequest;
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
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


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
    public String createChecksum(BaseRequest baseRequest) {
        StringBuilder stringBuffer = new StringBuilder();
            stringBuffer.append(baseRequest.getTmsDt()).append(baseRequest.getTmsTm())
                    .append(baseRequest.getTrnSrno()).append(baseRequest.getPrrstDscd())
                    .append(StringUtils.isBlank(baseRequest.getActNo())?"":baseRequest.getActNo())
                    .append(baseRequest.getLinkType()!=null? String.valueOf(baseRequest.getLinkType()):"")
                    .append(StringUtils.isBlank(baseRequest.getTelNo())?"":baseRequest.getTelNo())
                    .append(StringUtils.isBlank(baseRequest.getCusNm())?"":baseRequest.getCusNm())
                    .append(StringUtils.isBlank(baseRequest.getCusIdNoCd())?"":baseRequest.getCusIdNoCd())
                    .append(StringUtils.isBlank(baseRequest.getCusIdNo())?"":baseRequest.getCusIdNo())
                    .append(StringUtils.isBlank(baseRequest.getRefNo())?"":baseRequest.getRefNo())
                    .append(StringUtils.isBlank(baseRequest.getOtpNo())?"":baseRequest.getOtpNo())
                    .append(StringUtils.isBlank(baseRequest.getTokenId())?"":baseRequest.getTokenId())
                    .append(StringUtils.isBlank(baseRequest.getCurCd())?"":baseRequest.getCurCd())
                    .append(StringUtils.isBlank(baseRequest.getEtcCtt())?"":baseRequest.getEtcCtt())
                    .append(StringUtils.isBlank(baseRequest.getTrnAmt())?"":baseRequest.getTrnAmt())
                    .append(StringUtils.isBlank(baseRequest.getOrgTrnSrno())?"":baseRequest.getOrgTrnSrno())
                    .append(StringUtils.isBlank(baseRequest.getOrgTmsDt())?"":baseRequest.getOrgTmsDt());

        String checksum = null;
        try {
            checksum = ChecksumUntil.encryptChecksum(stringBuffer.toString(), ChecksumUntil.readPKCS8PrivateKey("private-to-partner.pem"));
        } catch (Exception e) {
            throw new InternalException("", null, "An error occurred in the system");
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
            throw new InternalException("8000", linkReq.getTrnSrno(), "APIC system error");
        }
        //check chữ kí
        boolean verifyChecksum = false;
        try {
            verifyChecksum = ChecksumUntil.verifyChecksum(reqChecksum, encryptedChecksum, ChecksumUntil.readX509PublicKey("public-to-partner.pem"));
        } catch (Exception e) {
            throw new InternalException("8000", linkReq.getTrnSrno(), "APIC system error");
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

        log.info("request core--------------------------");
        log.info(mappingInput);

        // chuyển chuỗi resquest đã mã hóa sang một hệ thống khác
        String apiResponse = apiCallUntil.makePostRequest(apiLink, mappingInput);

        log.info("respon core--------------------------");
        log.info(apiResponse);

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
        String res = linkRes.getTmsDt() + linkRes.getTmsTm() + linkRes.getTrnSrno() + linkRes.getRefNo() + linkRes.getRspCd() + linkRes.getTokenId();
        System.out.println(res);
        //gen ra checksum
        String genCheckSum = null;
        try {
            genCheckSum = ChecksumUntil.encryptChecksum(res, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem"));
        } catch (Exception e) {
            throw new InternalException("8000", linkReq.getTrnSrno(), "APIC system error");
        }
        linkRes.setCheckSum(genCheckSum);

        log.info("respon api link--------------------------");
        log.info(linkRes.toString());

        return linkRes;
    }

    @Override
    public VerifyLinkRes verifyLink(VerifyLinkReq verifyLinkReq) {
        VerifyLinkRes res = new VerifyLinkRes();
        String reqChecksum = verifyLinkReq.getTmsDt() + verifyLinkReq.getTmsTm() + verifyLinkReq.getTrnSrno() + verifyLinkReq.getPrrstDscd() + verifyLinkReq.getRefNo() + verifyLinkReq.getOtpNo();
        String encryptedChecksum = null;
        try {
            encryptedChecksum = ChecksumUntil.encryptChecksum(reqChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-partner.pem"));
        } catch (Exception ex) {
            throw new InternalException("8000", verifyLinkReq.getTrnSrno(), "APIC system error");
        }
        boolean verifyChecksum = false;
        try {
            verifyChecksum = ChecksumUntil.verifyChecksum(reqChecksum, encryptedChecksum, ChecksumUntil.readX509PublicKey("public-to-partner.pem"));
        } catch (Exception e) {
            throw new InternalException("8000", verifyLinkReq.getTrnSrno(), "APIC system error");
        }
        if (!verifyChecksum) {
            throw new ValidationException("8001", verifyLinkReq.getTrnSrno(), "invalid signature");
        }
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "EW001";
        String apCusNo = "123456";
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

        log.info("request core--------------------------");
        log.info(mappingInput);

        String apiResponse = apiCallUntil.makePostRequest(apiLinkConfirm, mappingInput);

        log.info("respon core--------------------------");
        log.info(apiResponse);

        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse); // converts xml to json
        res.setTmsDt(json.get("tmsDt").toString());
        res.setTmsTm(json.get("tmsTm").toString());
        res.setTrnSrno(json.get("trnSrno").toString());
        res.setRspCd(json.get("rspCd").toString());
        res.setTokenId(json.optString("tokenId", ""));

        String re = res.getTmsDt() + res.getTmsTm() + res.getTrnSrno() + res.getRspCd() + res.getTokenId();

        //gen ra checksum
        String genCheckSum = null;
        try {
            genCheckSum = ChecksumUntil.encryptChecksum(re, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem"));
        } catch (Exception e) {
            throw new BusinessException("8000", null, "APIC System Error");
        }
        res.setCheckSum(genCheckSum);

        log.info("respon api verify link--------------------------");
        log.info(res.toString());
        return res;
    }

    @Override
    public UnlinkRes unlink(UnlinkReq unlinkReq) {
        UnlinkRes unlinkRes = new UnlinkRes();
        String reqChecksum = unlinkReq.getTmsDt() + unlinkReq.getTmsTm() + unlinkReq.getTrnSrno() + unlinkReq.getPrrstDscd() + unlinkReq.getTokenId() + unlinkReq.getTelNo();
        String encryptedChecksum = null;
        try {
            encryptedChecksum = ChecksumUntil.encryptChecksum(reqChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-partner.pem"));
        } catch (Exception ex) {
            throw new BusinessException("8000", null, "APIC System Error");
        }
        boolean verifyChecksum = false;
        try {
            verifyChecksum = ChecksumUntil.verifyChecksum(reqChecksum, encryptedChecksum, ChecksumUntil.readX509PublicKey("public-to-partner.pem"));
        } catch (Exception e) {
            throw new BusinessException("8000", null, "APIC System Error");
        }
        if (!verifyChecksum) {
            throw new ValidationException("8001", unlinkReq.getTrnSrno(), "invalid signature");
        }
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "EW001";
        String apCusNo = "123456";
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

        log.info("request core--------------------------");
        log.info(mappingInput);

        String apiResponse = apiCallUntil.makePostRequest(apiUnLink, mappingInput);

        log.info("respon core--------------------------");
        log.info(apiResponse);

        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse);
        unlinkRes.setTmsDt(json.get("tmsDt").toString());
        unlinkRes.setTmsTm(json.get("tmsTm").toString());
        unlinkRes.setTrnSrno(json.get("trnSrno").toString());
        unlinkRes.setRspCd(json.get("rspCd").toString());
        String ress = unlinkRes.getTmsDt() + unlinkRes.getTmsTm() + unlinkRes.getTrnSrno() + unlinkRes.getRspCd();
        String genCheckSum = null;
        try {
            genCheckSum = ChecksumUntil.encryptChecksum(ress, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem"));
        } catch (Exception e) {
            throw new BusinessException("8000", null, "APIC System Error");
        }
        unlinkRes.setCheckSum(genCheckSum);

        log.info("respon api unlink--------------------------");
        log.info(unlinkRes.toString());

        return unlinkRes;
    }
}
