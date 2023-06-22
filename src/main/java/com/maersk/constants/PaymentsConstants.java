package com.maersk.constants;

public class PaymentsConstants {
    private PaymentsConstants(){}
    public static final String API_VERSION = "v1";
    // ERRORS
    public static final String PREFIX_SERVER_ERROR = "SRV";
    public static final String PREFIX_CLIENT_ERROR = "CLI";
    // CLIENT ERRORS
    public static final String UNPROCESSABLE_ENTITY =  "003";
    public static final String BAD_REQUEST = "001";
    public static final String UNAUTHORIZED = "002";
    public static final String NOT_FOUND = "004";
    public static final String CONFLICT = "009";

    // SERVER ERRORS
    public static final String INTERNAL_SERVER_ERROR = "001";

    public static final String RESOURCE_TOKENS = "/tokens";
    public static final String RESOURCE_CARDS = "/cards";
    public static final String RESOURCE_CARD = "/card";
    public static final Integer VALUE_VISA_MASTERCARD = 123;
    public static final Integer VALUE_AMEX = 4532;
    public static final String REQ_HEADER_AUTHORIZATE = "Authorization";

}
