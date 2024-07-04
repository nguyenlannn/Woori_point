package com.example.woori_base.entity;

import com.example.woori_base.enums.CusIdNoCdEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
@Entity(name = "partner")
public class PartnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int partnerId;
    private String cusNm;//tên khách hàng//NGUYEN VAN A
    private String telNo;//sđt khách hàng
    private String actNo; //số tài khoản/ thẻ của khách hàng
    private CusIdNoCdEnum cusIdNoCd;//loại id
    private String cusIdNo; //số id thẻ
    private Integer isSignature;
    private String signature;
    private Date createAt;
    private String createBy;
}
