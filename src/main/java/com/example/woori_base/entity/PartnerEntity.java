package com.example.woori_base.entity;

import com.example.woori_base.enums.CusIdNoCdEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@Entity(name = "partner")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PartnerEntity {
    @Id
    private String apCusNo;//mã khách hàng-duy nhất

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String cusNm;//tên khách hàng//NGUYEN VAN A

    @Column(nullable = false, unique = true)
    private String password;// mật khẩu khách hàng

    private String telNo;//sđt khách hàng

    private String actNo; //số tài khoản/ thẻ của khách hàng
    private CusIdNoCdEnum cusIdNoCd;//loại id
    private String cusIdNo; //số id thẻ
    private Integer isSignature;//chữ kí
    private String signature;
    private Date createAt;
    private String createBy;

}
