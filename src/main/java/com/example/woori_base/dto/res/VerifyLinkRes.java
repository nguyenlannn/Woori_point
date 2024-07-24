package com.example.woori_base.dto.res;

import com.example.woori_base.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyLinkRes //extends BaseResponse
{
    private String tmsDt;
    private String tmsTm;
    private String trnSrno;
    private String tokenId;
    private String rspCd;
    private String checkSum;
}
