package com.maersk.service;

import com.maersk.dto.request.CardPaymentRequestDTO;
import com.maersk.dto.response.CardPaymentResponseDTO;
import com.maersk.model.PaymentCard;
import com.maersk.repository.PaymentCardRepository;
import com.maersk.util.PaymentsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

    final HsmService hsmService;
    final PaymentCardRepository paymentCardRepository;

    @Override
    public CardPaymentResponseDTO getToken(CardPaymentRequestDTO cardPaymentRequestDTO, String token) throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        validateHeaderTokens(token);
        validateCardPaymentRequestDTO(cardPaymentRequestDTO);
        return saveToken(cardPaymentRequestDTO);
    }

    public CardPaymentResponseDTO saveToken(CardPaymentRequestDTO payment) throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        PaymentCard paymentCard = new PaymentCard();
        Random random = new Random();
        Long n = random.nextLong(Long.parseLong("999999999999999000"),Long.parseLong("999999999999999999"));
        paymentCard.setIdCard(Long.toHexString(n));
        paymentCard.setCardNumber(hsmService.encode(payment.getCardNumber().toString()));
        paymentCard.setEmail(hsmService.encode(payment.getEmail()));
        paymentCard.setExpireMoth(hsmService.encode(payment.getExpireMoth()));
        paymentCard.setExpireYear(hsmService.encode(payment.getExpireYear()));
        paymentCard=paymentCardRepository.save(paymentCard);
        CardPaymentResponseDTO cardPaymentResponseDTO = new CardPaymentResponseDTO();
        cardPaymentResponseDTO.setToken(paymentCard.getIdCard());
        return cardPaymentResponseDTO;
    }
    public void validateCardPaymentRequestDTO(CardPaymentRequestDTO cardPaymentRequestDTO){
        PaymentsUtil.notNull(cardPaymentRequestDTO,"the card payment cannot be null.");
        PaymentsUtil.notNull(cardPaymentRequestDTO.getCardNumber(),"the card number cannot be null.");
        PaymentsUtil.notNull(cardPaymentRequestDTO.getCvv(),"the cvv cannot be null.");
        PaymentsUtil.notNull(cardPaymentRequestDTO.getEmail(),"the email cannot be null.");
        PaymentsUtil.notNull(cardPaymentRequestDTO.getExpireYear(),"the expire year cannot be null.");
        PaymentsUtil.notNull(cardPaymentRequestDTO.getExpireMoth(),"the expire moth cannot be null.");
        PaymentsUtil.validateLuhn(cardPaymentRequestDTO.getCardNumber());
        PaymentsUtil.validateCard(cardPaymentRequestDTO.getCvv());
        PaymentsUtil.validateEmail(cardPaymentRequestDTO.getEmail());
        PaymentsUtil.validateExpirationYear(Integer.parseInt(cardPaymentRequestDTO.getExpireYear()));
        PaymentsUtil.validateMonth(Integer.parseInt(cardPaymentRequestDTO.getExpireMoth()));
    }
    public void validateHeaderTokens(String tokens){
        PaymentsUtil.notNull(tokens,"the token cannot be null.");
        PaymentsUtil.validateHeaderToken(tokens);

    }
}
