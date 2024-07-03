package com.example.woori_base.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final int errorCode;
    private final Object data;

    public BadRequestException(int errorCode, Object data, String message){
        super(message);
        this.errorCode=errorCode;
        this.data=data;
    }
}
