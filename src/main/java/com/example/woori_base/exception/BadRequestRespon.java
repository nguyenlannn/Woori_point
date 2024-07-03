package com.example.woori_base.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestRespon {
    private int errorCode;
    private String message;
    private Object data;

    public BadRequestRespon(int errorCode, String message, Object data){
        this.errorCode=errorCode;
        this.message=message;
        this.data=data;
    }
}
