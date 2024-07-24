package com.example.woori_base.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopUpConfirmReq {

    @Size(max = 8,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String tmsDt;

    @Size(max = 6,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String tmsTm;

    @Size(max = 20,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String trnSrno;

    @Size(max = 3,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String prrstDscd;

    @Size(max = 25,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String tokenId;

    @NotNull(message = "0031;The required field is omitted. Check the detailed content")
    private Integer trnAmt;

    @Size(max = 20,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String refNo;

    @Size(max = 6,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String otpNo;

    @Size(max = 3,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String curCd;

    @NotBlank
    private String checkSum;

    @Size(max = 200,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String etcCtt;
}
