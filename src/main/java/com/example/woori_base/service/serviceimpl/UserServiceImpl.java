package com.example.woori_base.service.serviceimpl;

import com.example.woori_base.dto.req.*;
import com.example.woori_base.dto.res.*;
import com.example.woori_base.exception.InternalServerErrorException;
import com.example.woori_base.service.UserService;
import com.example.woori_base.until.ApiCallUntil;
import com.example.woori_base.until.HeaderGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private ApiCallUntil apiCallUntil;

    @Override
    public LinkRes postLink(String apiUrl, LinkReq linkReq) {


        String random = RandomStringUtils.random(8, "1234567890");//random một chuỗi có 8 kí tự số

        String msgDscd = "EW001";//mã mặc định
        String apCusNo = " partner code";//mã đối tác

        HeaderGenerator headerGenerator=new HeaderGenerator();

        String mappingInput = headerGenerator.getClass() + "<root dataType=\"IN\"><T271010VRequest seq=\"1\" device=\"TM\">" +
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
                + "</params></T271010VRequest></root>"
                + "\n";

        String apiResponse = null;
        try {
            apiResponse = apiCallUntil.makePostRequest(apiUrl,mappingInput);
        } catch (IOException e) {
            throw new InternalServerErrorException("Gateway timeout");
        }
//kiểm tra xem respon có null không
        if(apiResponse==null){
            throw new NullPointerException("không có giá trị trả về");
        }
        //cắt chuỗi
        int start=apiResponse.indexOf("<params>");
        int end=apiResponse.indexOf("</T271010VRequest>");
        String subApiResponse = apiResponse.substring(start,end);

			JSONObject json = XML.toJSONObject(subApiResponse); // converts xml to json
            LinkRes linkRes=new LinkRes();
            linkRes.setTmsDt(json.getString("tmsDt"));
            linkRes.setTmsTm(json.getString("tmsTm"));
            linkRes.setTrnSrno(json.getString("trnSrno"));
            linkRes.setRefNo(json.getString("refNo"));
            linkRes.setTokenId(json.getString("tokenId"));
            linkRes.setErrEtc(json.getString("errEtc"));
        return linkRes;
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
