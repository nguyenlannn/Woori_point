package com.example.woori_base.dto.req;

import com.example.woori_base.enums.CusIdNoCdEnum;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LinkReq {

    @Column(length = 8, nullable = false)
    private String tmsDt;

    @Column(length = 6, nullable = false)
    private String tmsTm;

    @Column(length = 20)
    private String trnSrno;

    @Column(length = 3, nullable = false)
    private String prrstDscd;

    @Column(length = 16, nullable = false)
    private String actNo;

    @Column(nullable = false)
    private Integer linkType;

    @Column(length = 20)
    private String telNo;

    @Column(length = 300, nullable = false)
    private String cusNm;

    @Column(length = 2, nullable = false)
    private CusIdNoCdEnum cusIdNoCd;

    @Column(length = 50, nullable = false)
    private String cusIdNo;

    @Column(nullable = false)
    private String checkSum;
}
