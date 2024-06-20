package com.example.woori_base.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkReq {
    private String tmsDt;
    private String tmsTm;
    private String trnSrno;
    private String prrstDscd;
    private String actNo;
    private Integer linkType;
    private String telNo;
    private String cusNm;
    private String cusIdNoCd;
    private String cusIdNo;
    private String checkSum;
}
