package com.example.woori_base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class BusinessException extends RuntimeException{
    private String errorCode;
    private Object data;

    public  BusinessException(String errorCode, String message, Object data) {
        super(message);
        this.errorCode=errorCode;
        this.data=data;
    }

    public  BusinessException(String message) {
        super(message);
    }
}
