package com.example.woori_base.dto.req;

import com.example.woori_base.entity.PartnerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class UserReq {
    private  String name;
    private  String email;
    private  String password;
    private String telNo;

    public PartnerEntity toPartnerEntity() {
        return PartnerEntity.builder()
                .cusNm(name)
                .email(email)
                .password(password)
                .telNo(telNo)
                .build();
    }
}
