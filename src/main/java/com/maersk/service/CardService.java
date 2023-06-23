package com.maersk.service;

import com.maersk.dto.response.CardResponseDTO;

public interface CardService {
    CardResponseDTO getCard(String card, String token) throws Exception;

}
