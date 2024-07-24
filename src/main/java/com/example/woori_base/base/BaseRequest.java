package com.example.woori_base.base;

import com.example.woori_base.enums.CusIdNoCdEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseRequest {
    private String tmsDt;
    private String tmsTm;
    private String trnSrno;
    private String actNo;
    private Integer linkType;
    private String cusNm;
    private String telNo;
    private String prrstDscd;
    private String cusIdNoCd;
    private String cusIdNo;
    private String refNo;
    private String otpNo;
    private String tokenId;
    private String curCd;
    private String etcCtt;
    private String trnAmt;
    private String orgTrnSrno;
    private String orgTmsDt;
}
