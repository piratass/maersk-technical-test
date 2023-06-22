package com.maersk.service;

import com.maersk.dto.request.CardPaymentRequestDTO;
import com.maersk.dto.response.CardPaymentResponseDTO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface TokenService {
    CardPaymentResponseDTO getToken(CardPaymentRequestDTO cardPaymentRequestDTO,String token) throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
}
