package com.example.woori_base.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SecureRegistrationNoRes {
    private String customerId;
    private String membershipLevel;
    private String membershipPoint;
}
