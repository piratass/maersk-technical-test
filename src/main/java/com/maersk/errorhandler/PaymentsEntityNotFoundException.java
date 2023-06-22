package com.maersk.errorhandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentsEntityNotFoundException extends RuntimeException {


	public PaymentsEntityNotFoundException() {
		super();
	}

	public PaymentsEntityNotFoundException(String message) {
		super(message);
		log.error(message);
	}
}
