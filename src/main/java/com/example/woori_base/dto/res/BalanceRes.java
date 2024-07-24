package com.example.woori_base.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceRes {
    private String tmsDt;
    private String tmsTm;
    private String trnSrno;
    private Integer trnAmt;
    private String rspCd;
    private String checksum;
}
