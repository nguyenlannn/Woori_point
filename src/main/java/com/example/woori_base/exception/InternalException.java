package com.example.woori_base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class InternalException extends RuntimeException {

    private String errorCode;
    private Object data;

    public InternalException(String errorCode,String message, Object data) {
        super(message);
        this.errorCode=errorCode;
        this.data=data;
    }
}
