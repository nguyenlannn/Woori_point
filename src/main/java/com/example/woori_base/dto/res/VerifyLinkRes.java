package com.example.woori_base.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerifyLinkRes {
    private String tmsDt;
    private String tmsTm;
    private String trnSrno;
    private String tokenId;
    private String rspCd;
    private String checkSum;
}
