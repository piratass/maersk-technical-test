package com.maersk.errorhandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentsEntityUnprocessableException extends RuntimeException {

    public PaymentsEntityUnprocessableException() {
        super();
    }

    public PaymentsEntityUnprocessableException(String message) {
        super(message);
        log.error(message);
    }
}
