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
public class TopupRes {
    private String tmsDt;
    private String tmsTm;
    private String trnSrno;
    private String rspCd;
    private String checksum;
}
