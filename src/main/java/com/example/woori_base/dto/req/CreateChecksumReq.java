package com.example.woori_base.dto.req;

import com.example.woori_base.enums.CusIdNoCdEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateChecksumReq {

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

    @NotNull(message = "0031;The required field is omitted. Check the detailed content")
    private Integer linkType;

    @Size(max = 20,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String telNo;

    @Size(max = 300,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String cusNm;

    @NotNull(message = "0031;The required field is omitted. Check the detailed content")
    private CusIdNoCdEnum cusIdNoCd;

    @Size(max = 50,message = "0031;The required field is omitted. Check the detailed content")
    @NotBlank
    private String cusIdNo;
}
