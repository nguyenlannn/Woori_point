package com.example.woori_base.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionRes {
    private String customerId;
    private String memberShipLevel;
    private String membershipPoint;
}
