package com.maersk.errorhandler;

import lombok.Data;

@Data
public class PaymentsValidationError extends PaymentsSubError {
    private String object;
    private String field;
    private Object rejectValue;
    private String message;

    public PaymentsValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public PaymentsValidationError(String object, String field, Object rejectValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectValue = rejectValue;
        this.message = message;
    }
}
