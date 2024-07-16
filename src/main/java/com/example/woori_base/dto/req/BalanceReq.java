package com.example.woori_base.dto.req;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BalanceReq {

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

    @Size(max = 16,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String actNo;

    @NotBlank
    private String checkSum;
}
