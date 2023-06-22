package com.maersk.errorhandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentsUnauthorizedException extends RuntimeException {

    public PaymentsUnauthorizedException() {
        super();
    }

    public PaymentsUnauthorizedException(String message) {
        super(message);
        log.error(message);
    }

}
