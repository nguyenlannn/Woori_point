package com.example.woori_base.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private String tmsDt;
    private String tmsTm;
    private String trnSrno;
    private String rspCd;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tokenId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errEtc;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String checkSum;
}
