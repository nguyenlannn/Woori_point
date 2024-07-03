package com.example.woori_base.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkRes {
    private String tmsDt;
    private String tmsTm;
    private String trnSrno;
    private String tokenId;
    private String refNo;
    private String rspCd;
    private String checkSum;
    private String errEtc;
}
