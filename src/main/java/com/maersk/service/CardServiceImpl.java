package com.maersk.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.maersk.dto.response.CardResponseDTO;
import com.maersk.model.PaymentCard;
import com.maersk.repository.PaymentCardRepository;
import com.maersk.util.PaymentsUtil;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    final PaymentCardRepository paymentCardRepository;
    final CardResponseDTO cardResponseDTO;
    final HsmService hsmService;

    @Override public CardResponseDTO getCard(final String idCard, final String token) throws Exception {
        PaymentsUtil.validateHeaderToken(token);
        Optional<PaymentCard> result = Optional.ofNullable(
                paymentCardRepository.findById(idCard).orElseThrow(() -> new Exception("not result")));
        cardResponseDTO.setCardNumber(hsmService.decode(result.get().getCardNumber()));
        cardResponseDTO.setId(hsmService.decode(result.get().getId()));
        cardResponseDTO.setEmail(hsmService.decode(result.get().getEmail()));
        cardResponseDTO.setExpireYear(hsmService.decode(result.get().getExpireYear()));
        cardResponseDTO.setExpireMoth(hsmService.decode(result.get().getExpireMoth()));
        return cardResponseDTO;
    }
}