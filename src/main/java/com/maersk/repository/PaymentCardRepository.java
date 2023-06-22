package com.maersk.repository;

import com.maersk.model.PaymentCard;
import org.springframework.data.repository.CrudRepository;

public interface PaymentCardRepository extends CrudRepository<PaymentCard,String> {
}
