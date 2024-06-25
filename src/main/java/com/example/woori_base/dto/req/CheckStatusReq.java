package com.example.woori_base.dto.req;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckStatusReq {
    @Column(length = 8, nullable = false)
    private String tmsDt;

    @Column(length = 6, nullable = false)
    private String tmsTm;

    @Column(length = 20)
    private String trnSrno;

    @Column(length = 3, nullable = false)
    private String prrstDscd;

    @Column(length = 20, nullable = false)
    private String orgTrnSrno;

    @Column(length = 8, nullable = false)
    private String orgTmsDt;

    @Column(nullable = false)
    private String checksum;
}
