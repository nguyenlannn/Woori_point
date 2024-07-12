package com.example.woori_base.exception;

import com.example.woori_base.base.BaseResponse;
import com.example.woori_base.until.ChecksumUntil;
import com.example.woori_base.until.DateUntil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleValidation(MethodArgumentNotValidException e){
        BaseResponse baseResponse=new BaseResponse();
//todo cần data lấy về từ request-nhưng mà có chỗ nào kiểm tra cái validate này đâu mà lấy đc
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<BaseResponse> handleBadRequestException(ValidationException e) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setTmsTm(DateUntil.convertTmsTm());
        baseResponse.setTmsDt(DateUntil.convertTmsDt());
        baseResponse.setTrnSrno((String) e.getData());
        baseResponse.setErrEtc(e.getMessage());
        baseResponse.setRspCd(String.valueOf(e.getErrorCode()));
        if(!"8001".equals(e.getErrorCode())){
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
        BaseResponse baseResponse=new BaseResponse();
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
        BaseResponse baseResponse=new BaseResponse();
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
