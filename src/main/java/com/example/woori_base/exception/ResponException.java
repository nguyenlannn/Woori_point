package com.example.woori_base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ResponException extends RuntimeException{

    private final String errorCode;

    public ResponException(String errorCode, String message){
        super(message);
        this.errorCode=errorCode;
    }
}
