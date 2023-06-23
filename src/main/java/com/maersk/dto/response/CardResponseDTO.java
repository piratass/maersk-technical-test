package com.maersk.dto.response;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class CardResponseDTO {
    String id;
    @JsonProperty("card_number")
    String cardNumber;
    @JsonProperty("expire_moth")
    String  expireMoth;
    @JsonProperty("expire_year")
    String  expireYear;
    String  email;
}