package com.maersk.controller;

import com.maersk.constants.PaymentsConstants;
import com.maersk.dto.request.CardPaymentRequestDTO;
import com.maersk.dto.response.CardPaymentResponseDTO;
import com.maersk.dto.response.CardResponseDTO;
import com.maersk.service.CardService;
import com.maersk.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping(PaymentsConstants.API_VERSION + PaymentsConstants.RESOURCE_CARDS)
public class CardController {
    final CardService cardService;
    @GetMapping(PaymentsConstants.RESOURCE_CARD)
    public ResponseEntity<CardResponseDTO> getCard(@Valid @RequestParam String idCard,
                                                   @RequestHeader(value = PaymentsConstants.REQ_HEADER_AUTHORIZATE) String token)
            throws Exception {
        return new ResponseEntity<>(cardService.getCard(idCard,token),HttpStatus.OK);
    }
}