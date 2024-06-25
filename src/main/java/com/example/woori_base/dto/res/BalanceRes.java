package com.example.woori_base.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BalanceRes {
    private String tmsDt;
    private String tmsTm;
    private String trnSrno;
    private Integer trnAmt;
    private String rspCd;
    private String checksum;
    private String errEtc;
}
