package com.example.woori_base.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointUsageRes {
    private String transactionId;
    private String transactionTime;
    private String totalPointAmount;
}
