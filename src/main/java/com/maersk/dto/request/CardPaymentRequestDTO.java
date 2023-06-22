package com.maersk.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CardPaymentRequestDTO {
    @JsonProperty("card_number")
    @Size( min = 13, max = 16, message = "The allowed size is from 13 to 16 characters")
    Long cardNumber;
    @Size( min = 3, max = 4, message = "The allowed size is from 3 to 4 characters")
    Integer cvv;
    @JsonProperty("expire_moth")
    @Size( min = 1, max = 2, message = "The allowed size is from 1 to 2 characters")
    String  expireMoth;
    @JsonProperty("expire_year")
    @Size(  max = 4, message = "The allowed size is from 4 characters")
    String  expireYear;
    @Size( min = 5, max = 100, message = "The allowed size is from 5 to 100 characters")
    String  email;
}
