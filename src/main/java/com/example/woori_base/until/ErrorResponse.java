package com.example.woori_base.until;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int errorCode;
        private String message;
        private Object data;

        public ErrorResponse(int errorCode, String message, Object data) {
            this.errorCode = errorCode;
            this.message = message;
            this.data = data;
        }

        public ErrorResponse(int errorCode, String message){
            this.errorCode = errorCode;
            this.message = message;
        }

    public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

    public void setMessage(String message) {
            this.message = message;
        }

    public void setData(Object data) {
            this.data = data;
        }
}
