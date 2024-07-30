package com.example.woori_base.service.serviceimpl;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import com.example.woori_base.exception.InternalException;
import com.example.woori_base.exception.ResponException;
import com.example.woori_base.service.MembershipPointService;
import com.example.woori_base.until.ApiCallUntil;
import com.example.woori_base.until.CipherIvUntil;
import com.example.woori_base.until.DateUntil;
import com.example.woori_base.until.HeaderGenerator;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class MembershipPointServiceImpl implements MembershipPointService {

    private final ApiCallUntil apiCallUntil;

    @Value("${api.urls.check-user-point}")
    private String apiCheckUser;

    @Value("${api.urls.connection-point}")
    private String apiConnection;

    @Value("${api.urls.secure-registration-point}")
    private String apiSecureRegistration;

    @Value("${api.urls.detail-info-search-point}")
    private String apiDetailInfoSearch;

    @Value("${api.urls.disconnection-point}")
    private String apiDisconnection;

    @Value("${api.urls.points-seeking-point}")
    private String apiPointsSeeking;

    @Value("${api.urls.points-usage-point}")
    private String apiPointsUsage;

    @Value("{cipher-key}")
    private String cipherkey;

    @Override
    public CheckUserRes checkUer(CheckUserReq checkUserReq) {

        CheckUserRes checkUserRes=new CheckUserRes();
        List<Customer> customerList=new ArrayList<>();
        Customer customer=new Customer();

        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "AP001";//mã mặc định
        String apCusNo = "VNI001";//mã đối tác

        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T275001VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<apCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + DateUntil.convertTmsDt() + "</tmsDt>"
                + "<tmsTm>" + DateUntil.convertTmsTm() + "</tmsTm>"
                + "<mbrId>" + checkUserReq.getMembersShipId() + "</mbrId>"
                + "<trnType>" + checkUserReq.getMatchingType() + "</trnType>"
                + "<telNo>" + checkUserReq.getMatchingValue() + "</telNo>"
                + "<birTh>" + checkUserReq.getDateOfBirth() + "</birTh>"
                + "</params></T275001VRequest></root>"));

        log.info("request core--------------------------");
        log.info(mappingInput);
        String apiResponse = apiCallUntil.makePostRequest(apiCheckUser, mappingInput);
        log.info("respon core--------------------------");
        log.info(apiResponse);
        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse);
        if(!Objects.equals(json.get("rspCd").toString(), "0000")) {
            if (Objects.equals(json.get("rspCd").toString(), "0003")) {
                throw new ResponException("0003", json.get("errMsg").toString());
            }
            throw new InternalException(json.get("errCd").toString(), json.get("errMsg").toString());
        }
        customer.setCustomerId(json.get("cusId").toString());
        customer.setCustomerName(json.get("cusNm").toString());
        customer.setGender(json.get("genCd").toString());
        customer.setBrandBarcode(json.get("mbrCardNo").toString());

        customerList.add(customer);
        checkUserRes.setCustomerInfoList(customerList);
        return checkUserRes;
    }

    @Override
    public ConnectionRes connection(ConnectionReq connectionReq) {
        ConnectionRes res=new ConnectionRes();
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "AP002";//mã mặc định
        String apCusNo = "VNI001";//mã đối tác

        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T275001VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<ApCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + DateUntil.convertTmsDt() + "</tmsDt>"
                + "<tmsTm>" + DateUntil.convertTmsTm() + "</tmsTm>"
                + "<rqId>" + connectionReq.getRequestId() + "</rqId>"
                + "<mbrId>" + connectionReq.getMembershipId() + "</mbrId>"
                + "<cusId>" + connectionReq.getCustomerId() + "</cusId>"
                + "<barCd>" + connectionReq.getBarCode() + "</barCd>"
                + "</params></T275001VRequest></root>"));

        log.info("request core--------------------------");
        log.info(mappingInput);
        String apiResponse = apiCallUntil.makePostRequest(apiConnection, mappingInput);
        log.info("respon core--------------------------");
        log.info(apiResponse);
        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse);
        if(!Objects.equals(json.get("rspCd").toString(), "0000")) {
//            if (Objects.equals(json.get("rspCd").toString(), "8000")) {
//                throw new InternalException("8000", json.get("errMsg").toString());
//            }
            throw new InternalException(json.get("errCd").toString(), json.get("errMsg").toString());
        }
        res.setCustomerId(json.get("cusId").toString());
        res.setMemberShipLevel(json.get("level").toString());
        res.setMembershipPoint(json.get("usblAmt").toString());
        return res;
    }
    //mã hoá dữ liệu bằng cipher
    @Override
    public SecureRegistrationRes secureRegistration(SecureRegistrationReq secureRegistrationReq) {
        SecureRegistrationRes res=new SecureRegistrationRes();
        //todo chia thành 2 api đăng kí thành viên

        String decoded= null;
        try {
            decoded = CipherIvUntil.decrypt(secureRegistrationReq.getEncryptedRequestData(),
                        secureRegistrationReq.getIV(),secureRegistrationReq.getAuthenticationTag(),cipherkey);
        } catch (Exception e) {
            throw new InternalException("8000","APIC system error");
        }
        JSONObject jsonObject = new JSONObject(decoded);

        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "AP003";//mã mặc định
        String apCusNo = "VNI001";//mã đối tác
        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T275001VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<ApCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + DateUntil.convertTmsDt() + "</tmsDt>"
                + "<tmsTm>" + DateUntil.convertTmsTm() + "</tmsTm>"
                + "<rqId>" + jsonObject.get("requestId") + "</rqId>"
                + "<mbrId>" + jsonObject.get("membershipId") + "</mbrId>"
                + "<telNo>" + jsonObject.get("mobilePhoneNo") + "</telNo>"
                + "<birTh>" + jsonObject.get("dateOfBirth") + "</birTh>"
                + "<genCd>" + jsonObject.get("gender") + "</gencd>"
                + "<cusNm>" + jsonObject.get("customerName") + "</cusNm>"
                + "<eMail>" + jsonObject.get("emailAddress") + "</eMail>"
                + "<barCd>" + jsonObject.get("vanilaBarcode") + "</barCode>"
                + "<cusIdNoCd>" + jsonObject.get("idType") + "</cusIdNoCd>"
                + "<cusIdNo>" + jsonObject.get("idNumber") + "</cusIdNo>"
                + "<locNatCd>" + jsonObject.get("countryCode") + "</locNatCd>"
                + "<state>" + jsonObject.get("state") + "</state>"
                +"<district>" + jsonObject.get("district") + "</district>"
                +"<etcCtt1>" + jsonObject.get("detail1")+ "</etcCtt1>"
                +"<etcCtt2>" + jsonObject.get("detail2")+ "</etcCtt2>"
                + "</params></T275001VRequest></root>"));

        log.info("request core--------------------------");
        log.info(mappingInput);
        String apiResponse = apiCallUntil.makePostRequest(apiConnection, mappingInput);
        log.info("respon core--------------------------");
        log.info(apiResponse);

        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse);
        if(!Objects.equals(json.get("rspCd").toString(), "0000")) {
            if (Objects.equals(json.get("rspCd").toString(), "0001")) {
                throw new InternalException("0001", json.get("errMsg").toString());
            }
            throw new InternalException(json.get("errCd").toString(), json.get("errMsg").toString());
        }
        res.setCustomerId(json.get("cusId").toString());
        res.setMemberShipLevel(json.getString("level"));
        res.setMembershipPoint(json.get("usblAmt").toString());
        return res;
    }

    //không mã hoá
    @Override
    public SecureRegistrationNoRes secureRegistrationNo(SecureRegistrationNoReq secureRegistrationNoReq) {
        SecureRegistrationNoRes res=new SecureRegistrationNoRes();
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "AP003";//mã mặc định
        String apCusNo = "VNI001";//mã đối tác

        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T275001VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<ApCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + DateUntil.convertTmsDt() + "</tmsDt>"
                + "<tmsTm>" + DateUntil.convertTmsTm() + "</tmsTm>"
                + "<rqId>" + secureRegistrationNoReq.getRequestId() + "</rqId>"
                + "<mbrId>" + secureRegistrationNoReq.getMembershipId() + "</mbrId>"
                + "<telNo>" + secureRegistrationNoReq.getMobilePhoneNo() + "</telNo>"
                + "<birTh>" + secureRegistrationNoReq.getDateOfBirth() + "</birTh>"
                + "<genCd>" + secureRegistrationNoReq.getGender() + "</gencd>"
                + "<cusNm>" + secureRegistrationNoReq.getCustomerName() + "</cusNm>"
                + "<eMail>" + secureRegistrationNoReq.getEmailAddress() + "</eMail>"
                + "<barCd>" + secureRegistrationNoReq.getBarCode() + "</barCode>"
                + "<cusIdNoCd>" + secureRegistrationNoReq.getIdType() + "</cusIdNoCd>"
                + "<cusIdNo>" + secureRegistrationNoReq.getIdNumber() + "</cusIdNo>"
                + "<locNatCd>" + secureRegistrationNoReq.getCountryCode() + "</locNatCd>"
                + "<state>" + secureRegistrationNoReq.getState() + "</state>"
                +"<district>" + secureRegistrationNoReq.getDistrict() + "</district>"
                +"<etcCtt1>" + secureRegistrationNoReq.getDetail1()+ "</etcCtt1>"
                +"<etcCtt2>" + secureRegistrationNoReq.getDetail2()+ "</etcCtt2>"
                + "</params></T275001VRequest></root>"));

        log.info("request core--------------------------");
        log.info(mappingInput);
        String apiResponse = apiCallUntil.makePostRequest(apiConnection, mappingInput);
        log.info("respon core--------------------------");
        log.info(apiResponse);

        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse);
        if(!Objects.equals(json.get("rspCd").toString(), "0000")) {
            if (Objects.equals(json.get("rspCd").toString(), "0001")) {
                throw new InternalException("0001", json.get("errMsg").toString());
            }
            throw new InternalException(json.get("errCd").toString(), json.get("errMsg").toString());
        }
        res.setCustomerId(json.get("cusId").toString());
        res.setMembershipLevel(json.getString("level"));
        res.setMembershipPoint(json.get("usblAmt").toString());
        return res;
    }

    @Override
    public DetailInfoRes detailInfoSearch(DetailInfoReq detailInfoReq) {
        DetailInfoRes res=new DetailInfoRes();
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "AP004";//mã mặc định
        String apCusNo = "VNI001";//mã đối tác

        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T275001VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<ApCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + DateUntil.convertTmsDt() + "</tmsDt>"
                + "<tmsTm>" + DateUntil.convertTmsTm() + "</tmsTm>"
                + "<cusId>" + detailInfoReq.getCustomerId() + "</cusId>"
                + "</params></T275001VRequest></root>"));

        log.info("request core--------------------------");
        log.info(mappingInput);
        String apiResponse = apiCallUntil.makePostRequest(apiDetailInfoSearch, mappingInput);
        log.info("respon core--------------------------");
        log.info(apiResponse);
        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse);
        if(!Objects.equals(json.get("rspCd").toString(), "0000")) {
            throw new InternalException(json.get("errCd").toString(), json.get("errMsg").toString());
        }
        res.setAvailablePoint(json.get("usblAmt").toString());
        res.setMembershipLevel(json.get("level").toString());
        return res;
    }

    @Override
    public void disconnection(DisconnectionReq disconnectionReq) {
        DisconnectionRes res=new DisconnectionRes();
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "AP005";//mã mặc định
        String apCusNo = "VNI001";//mã đối tác

        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T275001VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<ApCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + DateUntil.convertTmsDt() + "</tmsDt>"
                + "<tmsTm>" + DateUntil.convertTmsTm() + "</tmsTm>"
                + "<cusId>" + disconnectionReq.getCustomerId() + "</cusId>"
                + "<barCd>" + disconnectionReq.getBarCode() + "</barCd>"
                + "<etcCtt>" + disconnectionReq.getReason() + "</etcCtt>"
                + "</params></T275001VRequest></root>"));

        log.info("request core--------------------------");
        log.info(mappingInput);
        String apiResponse = apiCallUntil.makePostRequest(apiDisconnection, mappingInput);
        log.info("respon core--------------------------");
        log.info(apiResponse);
        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse);
        if(!Objects.equals(json.get("rspCd").toString(), "0000")) {
            throw new InternalException(json.get("errCd").toString(), json.get("errMsg").toString());
        }
    }

    @Override
    public PointsSeekingRes pointsSeeking(PointsSeekingReq pointsSeekingReq) {
        PointsSeekingRes pointsSeeking=new PointsSeekingRes();
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "AP006";//mã mặc định
        String apCusNo = "VNI001";//mã đối tác

        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T275001VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<ApCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + DateUntil.convertTmsDt() + "</tmsDt>"
                + "<tmsTm>" + DateUntil.convertTmsTm() + "</tmsTm>"
                + "<cusId>" + pointsSeekingReq.getCustomerId() + "</cusId>"
                + "</params></T275001VRequest></root>"));

        log.info("request core--------------------------");
        log.info(mappingInput);
        String apiResponse = apiCallUntil.makePostRequest(apiPointsSeeking, mappingInput);
        log.info("respon core--------------------------");
        log.info(apiResponse);
        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse);
        if(!Objects.equals(json.get("rspCd").toString(), "0000")) {
            throw new InternalException(json.get("errCd").toString(), json.get("errMsg").toString());
        }
        pointsSeeking.setTotalPoints(json.get("totalAmt").toString());
        pointsSeeking.setSwappablePoints(json.get("usblAmt").toString());
        return pointsSeeking;
    }

    @Override
    public PointUsageRes pointUsage(PointUsageReq pointUsageReq) {
        PointUsageRes pointUsage=new PointUsageRes();
        String random = RandomStringUtils.random(8, "1234567890");
        String msgDscd = "AP007";//mã mặc định
        String apCusNo = "VNI001";//mã đối tác

        String mappingInput = String.valueOf(HeaderGenerator.generateMessage("<root dataType=\"IN\"><T275001VRequest seq=\"1\" device=\"TM\">" +
                "<params>"
                + "<msgTrno>" + random + "</msgTrno>"
                + "<msgDscd>" + msgDscd + "</msgDscd>"
                + "<ApCusNo>" + apCusNo + "</msgTrno>"
                + "<tmsDt>" + DateUntil.convertTmsDt() + "</tmsDt>"
                + "<tmsTm>" + DateUntil.convertTmsTm() + "</tmsTm>"
                + "<reqId>" + pointUsageReq.getRequestId() + "</reqId>"
                + "<cusId>" + pointUsageReq.getCustomerId() + "</cusId>"
                + "<usblAmt>" + pointUsageReq.getUsagePoints() + "</usblAmt>"
                + "</params></T275001VRequest></root>"));

        log.info("request core--------------------------");
        log.info(mappingInput);
        String apiResponse = apiCallUntil.makePostRequest(apiPointsUsage, mappingInput);
        log.info("respon core--------------------------");
        log.info(apiResponse);
        int start = apiResponse.indexOf("<params>");
        int end = apiResponse.indexOf("</params>");
        String subApiResponse = apiResponse.substring(start + "<params>".length(), end);

        JSONObject json = XML.toJSONObject(subApiResponse);
        if(!Objects.equals(json.get("rspCd").toString(), "0000")) {
            throw new InternalException(json.get("errCd").toString(), json.get("errMsg").toString());
        }
        pointUsage.setTransactionId(json.get("trnId").toString());
        pointUsage.setTransactionTime(json.get("trnTm").toString());
        pointUsage.setTotalPointAmount(json.get("totalAmt").toString());
        return pointUsage;
    }
}
