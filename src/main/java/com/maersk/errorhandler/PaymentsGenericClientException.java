package com.maersk.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.List;

@Slf4j
public class PaymentsGenericClientException extends RuntimeException {

    private HttpStatus httpStatus;
    private List<PaymentsSubError> subErrors;
    private String logType;

    public PaymentsGenericClientException() {
        super();
    }

    public PaymentsGenericClientException(String message, Throwable ex) {
        super(message, ex);
    }

    public PaymentsGenericClientException(String message) {
        super(message);
    }

    public PaymentsGenericClientException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        log.error(message);
    }

    public PaymentsGenericClientException(String message, HttpStatus httpStatus,
                                          List<PaymentsSubError> subErrors) {
        super(message);
        this.httpStatus = httpStatus;
        this.subErrors = subErrors;
        log.error(message);
    }

    public PaymentsGenericClientException(Throwable ex) {
        super(ex);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public List<PaymentsSubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<PaymentsSubError> subErrors) {
        this.subErrors = subErrors;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }
}
