package com.maersk.service;

import com.maersk.dto.response.CardResponseDTO;
import com.maersk.model.PaymentCard;
import com.maersk.repository.PaymentCardRepository;
import com.maersk.util.PaymentsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    final PaymentCardRepository paymentCardRepository;
    final HsmService hsmService;
    @Override
    public CardResponseDTO getCard(String card, String token) throws Exception {
        PaymentsUtil.validateHeaderToken(token);
        CardResponseDTO cardResponseDTO = new CardResponseDTO();
        Optional<PaymentCard> result = Optional.ofNullable(
                paymentCardRepository.findPaymentCardByIdCard(card).orElseThrow(() -> new Exception("not result")));
        cardResponseDTO.setCardNumber(hsmService.decode(result.get().getCardNumber()));
        cardResponseDTO.setId(result.get().getIdCard());
        cardResponseDTO.setEmail(hsmService.decode(result.get().getEmail()));
        cardResponseDTO.setExpireYear(hsmService.decode(result.get().getExpireYear()));
        cardResponseDTO.setExpireMoth(hsmService.decode(result.get().getExpireMoth()));
        return cardResponseDTO;
    }
}
