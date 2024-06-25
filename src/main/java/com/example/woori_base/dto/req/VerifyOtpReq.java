package com.example.woori_base.dto.req;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpReq {
    @Column(length = 8, nullable = false)
    private String tmsDt;

    @Column(length = 6, nullable = false)
    private String tmsTm;

    @Column(length = 20)
    private String trnSrno;

    @Column(length = 3, nullable = false)
    private String prrstDscd;

    @Column(length = 25, nullable = false)
    private String tokenId;

    @Column(nullable = false)
    private Integer trnAmt;

    @Column(length = 20, nullable = false )
    private String refNo;

    @Column(length = 6, nullable = false)
    private String otpNo;

    @Column(length = 3, nullable = false)
    private String curCd;

    @Column(nullable = false)
    private String checksum;

    @Column(length = 200, nullable = false)
    private String etcCtt;
}
