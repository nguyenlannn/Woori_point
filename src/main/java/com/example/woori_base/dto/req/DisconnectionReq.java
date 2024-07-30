package com.example.woori_base.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisconnectionReq {

    private String customerId;
    private String barCode;
    private String reason;
}
