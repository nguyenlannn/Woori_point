package com.example.woori_base.entity;

import com.example.woori_base.enums.CusIdNoCdEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class User {
    private String cusNm;//tên khách hàng//NGUYEN VAN A
    private String telNo;//sđt khách hàng
    private String actNo; //số tài khoản/ thẻ của khách hàng
    private CusIdNoCdEnum cusIdNoCd;//loại id
    private String cusIdNo; //số id thẻ
}
