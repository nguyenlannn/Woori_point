package com.example.woori_base.dto.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRes {
    private String token_type;
    private String accessToken;
    private Integer expiresIn;//thời gian hết hạn của token
    private String scope;//phạm vi truy cập
}
