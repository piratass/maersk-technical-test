package com.maersk.repository;

import com.maersk.model.PaymentCard;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentCardRepository extends CrudRepository<PaymentCard,Long> {
    Optional<PaymentCard> findPaymentCardByIdCard(String id);
}
