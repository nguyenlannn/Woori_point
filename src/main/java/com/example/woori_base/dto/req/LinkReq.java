package com.example.woori_base.dto.req;

import com.example.woori_base.enums.CusIdNoCdEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LinkReq {

    @Size(max = 8)
    @NotBlank
    private String tmsDt;

    @Size(max = 6)
    @NotBlank
    private String tmsTm;

    @Size(max = 20)
    @NotBlank
    private String trnSrno;

    @Size(max = 3)
    @NotBlank
    private String prrstDscd;

    @Size(max = 16)
    @NotBlank
    private String actNo;

    @NotNull
    private Integer linkType;

    @Size(max = 20)
    @Pattern(regexp = "\\d+")
    private String telNo;

    @Size(max = 300)
    private String cusNm;

    @NotNull
    private CusIdNoCdEnum cusIdNoCd;

    @Size(max = 50)
    @NotBlank
    private String cusIdNo;

    @NotBlank
    private String checkSum;
}
