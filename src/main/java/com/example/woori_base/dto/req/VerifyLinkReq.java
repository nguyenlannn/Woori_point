package com.example.woori_base.dto.req;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerifyLinkReq {

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

    @Size(max = 20,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String refNo;

    @Size(max = 6,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String otpNo;

    @NotBlank
    private String checkSum;
}
