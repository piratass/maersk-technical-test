package com.maersk.errorhandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentsConflictException extends RuntimeException {

    public PaymentsConflictException() {
        super();
    }

    public PaymentsConflictException(String message) {
        super(message);
        log.error(message);
    }


}