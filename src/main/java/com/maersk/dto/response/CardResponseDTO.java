package com.maersk.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
