package com.example.woori_base.exception;

import com.example.woori_base.base.BaseResponse;
import com.example.woori_base.until.ChecksumUntil;
import com.example.woori_base.until.DateUntil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<BaseResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) throws JsonProcessingException {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        BaseResponse response = new BaseResponse();
        response.setTmsTm(DateUntil.convertTmsTm());
        response.setTmsDt(DateUntil.convertTmsDt());

        Object req = ex.getTarget();
        assert req != null;
        JSONObject obj = new JSONObject(req);
        response.setTrnSrno(obj.optString("trnSrno", ""));

        String rspCd = "0002";
        String errEtc = "The required field is omitted";

        for (FieldError fieldError : fieldErrors) {
            String[] errorMessages = Objects.requireNonNull(fieldError.getDefaultMessage()).split(";");

            if ("0031".equals(errorMessages[0])) {
                rspCd = "0031";
                errEtc = errorMessages[1];
                break;
            }
        }
        response.setRspCd(rspCd);
        response.setErrEtc(errEtc);

        if (!"8001".equals(response.getRspCd())) {
            String InputChecksum = response.getTmsDt() + response.getTmsTm() + response.getRspCd()
                    + response.getErrEtc() + response.getTrnSrno();
            try {
                response.setCheckSum(ChecksumUntil.encryptChecksum(InputChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem")));
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String getRequestBody(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<BaseResponse> handleBadRequestException(ValidationException e) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setTmsTm(DateUntil.convertTmsTm());
        baseResponse.setTmsDt(DateUntil.convertTmsDt());
        baseResponse.setTrnSrno((String) e.getData());
        baseResponse.setErrEtc(e.getMessage());
        baseResponse.setRspCd(String.valueOf(e.getErrorCode()));
        if (!"8001".equals(e.getErrorCode())) {
            String InputChecksum = baseResponse.getTmsDt() + baseResponse.getTmsTm() + baseResponse.getRspCd()
                    + baseResponse.getErrEtc() + baseResponse.getTrnSrno();
            try {
                baseResponse.setCheckSum(ChecksumUntil.encryptChecksum(InputChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem")));
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);//400
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse> handleBusinessExceptions(BusinessException e) {
        BaseResponse baseResponse = new BaseResponse();
        //todo trả về respon của mình-ko kết nối với core được-ko lấy đc respon
        baseResponse.setTmsTm(DateUntil.convertTmsTm());
        baseResponse.setTmsDt(DateUntil.convertTmsDt());
        baseResponse.setTrnSrno((String) e.getData());
        baseResponse.setErrEtc(e.getMessage());
        baseResponse.setRspCd(String.valueOf(e.getErrorCode()));
        String InputChecksum = baseResponse.getTmsDt() + baseResponse.getTmsTm() + baseResponse.getRspCd()
                + baseResponse.getErrEtc() + baseResponse.getTrnSrno();
        try {
            baseResponse.setCheckSum(ChecksumUntil.encryptChecksum(InputChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem")));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.GATEWAY_TIMEOUT);//504
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<BaseResponse> handleInternalExceptions(InternalException e) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setTmsTm(DateUntil.convertTmsTm());
        baseResponse.setTmsDt(DateUntil.convertTmsDt());
        baseResponse.setTrnSrno((String) e.getData());
        baseResponse.setErrEtc(e.getMessage());
        baseResponse.setRspCd(String.valueOf(e.getErrorCode()));
        String InputChecksum = baseResponse.getTmsDt() + baseResponse.getTmsTm() + baseResponse.getRspCd()
                + baseResponse.getErrEtc() + baseResponse.getTrnSrno();
        try {
            baseResponse.setCheckSum(ChecksumUntil.encryptChecksum(InputChecksum, ChecksumUntil.readPKCS8PrivateKey("private-to-me.pem")));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.GATEWAY_TIMEOUT);//500
    }
}
