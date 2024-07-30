package com.example.woori_base.exception;

import com.example.woori_base.base.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(ResponException.class)
    public ResponseEntity handleInternalExceptions(ResponException ee) {
        BaseErrorResponse baseErrorResponse=new BaseErrorResponse(ee.getErrorCode(), ee.getMessage());
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.NOT_FOUND);//404
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        BaseErrorResponse baseErrorResponse=new BaseErrorResponse();
        String errorCode = "0002";
        String errorMessage = "The required field is omitted";
        for (FieldError fieldError : fieldErrors) {
            String[] errorMessages = Objects.requireNonNull(fieldError.getDefaultMessage()).split(";");

            if ("0031".equals(errorMessages[0])) {
                errorCode = "0031";
                errorMessage = errorMessages[1];
                break;
            }
        }
        baseErrorResponse.setErrorCode(errorCode);
        baseErrorResponse.setErrorMessage(errorMessage);
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity handleInternalExceptions(InternalException ee) {
        BaseErrorResponse baseErrorResponse=new BaseErrorResponse(ee.getErrorCode(), ee.getMessage());
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);//500
    }

}
