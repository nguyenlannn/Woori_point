package com.example.woori_base.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseErrorResponse {
    private String errorCode;
    private String errorMessage;
}
