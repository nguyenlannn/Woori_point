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
public class LinkRes {
    private String tmsDt;
    private String tmsTm;
    private String trnSrno;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tokenId;
    private String refNo;
    private String rspCd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String checkSum;
}
