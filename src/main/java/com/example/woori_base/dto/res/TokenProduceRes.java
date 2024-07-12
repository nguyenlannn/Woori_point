package com.example.woori_base.dto.res;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenProduceRes {
    private String assessToken;
    private String refreshToken;
}
