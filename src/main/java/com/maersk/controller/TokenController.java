package com.maersk.controller;

import com.auth0.jwt.interfaces.Header;
import com.maersk.dto.request.CardPaymentRequestDTO;
import com.maersk.dto.response.CardPaymentResponseDTO;
import com.maersk.constants.PaymentsConstants;
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
@RequestMapping(PaymentsConstants.API_VERSION + PaymentsConstants.RESOURCE_TOKENS)
public class TokenController {

    final TokenService tokenService;
    @PostMapping()
    public ResponseEntity<CardPaymentResponseDTO> getToken(@Valid @RequestBody CardPaymentRequestDTO cardPaymentRequestDTO ,
                                                           @RequestHeader(value = PaymentsConstants.REQ_HEADER_AUTHORIZATE) String token) throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return new ResponseEntity<>(tokenService.getToken(cardPaymentRequestDTO,token), HttpStatus.OK);
    }
}
