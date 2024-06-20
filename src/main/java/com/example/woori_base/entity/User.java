package com.example.woori_base.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    @Column(length = 8)
    private String tmsDt;
    @Column(length = 6)
    private String tmsTm;
    @Column(length = 20)
    private String trnSrno;
    @Column(length = 3)
    private String prrstDscd;
    private String actNo;
    private Integer linkType;
    private String telNo;
    private String cusNm;
    private String cusIdNoCd;
    private String cusIdNo;
    private String checkSum;
}
