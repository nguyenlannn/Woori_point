package com.example.woori_base.base;

import lombok.Builder;

@Builder
public class BaseRespon {
    private String message;
    private Integer errorCode;
    private Object data;
    private boolean success;

    public static BaseRespon error(String message) {
        return BaseRespon.builder()
                .success(false)
                .message(message).build();
    }

    public static BaseRespon error(String message, Integer errorCode){
        return BaseRespon.builder()
                .success(false)
                .errorCode(errorCode)
                .message(message).build();
    }

    public static BaseRespon error(String message, Integer errorCode, Object data){
        return BaseRespon.builder()
                .success(false)
                .errorCode(errorCode)
                .data(data)
                .build();
    }

    public static BaseRespon success(String message){
        return BaseRespon.builder()
                .success(true)
                .message(message)
                .build();
    }

    public static BaseRespon success(Object data){
        return BaseRespon.builder()
                .success(true)
                .data(data)
                .build();
    }

    public static BaseRespon success(String message, Object data){
        return BaseRespon.builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }
}
