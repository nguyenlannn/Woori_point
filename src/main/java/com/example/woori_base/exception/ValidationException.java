package com.example.woori_base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ValidationException extends RuntimeException {

    private final String errorCode;

    public ValidationException(String errorCode, Object data, String message){
        super(message);
        this.errorCode=errorCode;
    }
}
