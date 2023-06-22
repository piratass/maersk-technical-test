package com.maersk.errorhandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentsGenericServerException extends RuntimeException {

    private String code;

    public PaymentsGenericServerException() {
        super();
    }

    public PaymentsGenericServerException(String message) {
        super(message);
        log.error(message);
    }

    public PaymentsGenericServerException(String message, String code) {
        super(message);
        this.code = code;
        log.error(message);
    }

    public PaymentsGenericServerException(Throwable ex) {
        super(ex);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
