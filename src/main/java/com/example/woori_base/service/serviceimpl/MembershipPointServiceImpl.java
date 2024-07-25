package com.example.woori_base.service.serviceimpl;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import com.example.woori_base.exception.ResponException;
import com.example.woori_base.service.MembershipPointService;
import com.example.woori_base.until.ApiCallUntil;
import com.example.woori_base.until.DateUntil;
import com.example.woori_base.until.HeaderGenerator;
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
            throw new ResponException(json.get("errCd").toString(), json.get("errMsg").toString());
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
    public ConnecttionRes connection(ConnecttionReq connecttionReq) {
        return null;
    }

    @Override
    public SecureRegistrationRes secureRegistration(SecureRegistrationReq secureRegistrationReq) {
        return null;
    }

    @Override
    public DetailInfoRes detailInfoSearch(DetailInfoReq detailInfoReq) {
        return null;
    }

    @Override
    public DisconnectionRes disconnection(DisconnectionReq disconnectionReq) {
        return null;
    }

    @Override
    public PointsSeekingRes pointsSeeking(PointsSeekingReq pointsSeekingReq) {
        return null;
    }

    @Override
    public PointUsageRes pointUsage(PointUsageReq pointUsageReq) {
        return null;
    }
}
