package com.maersk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_payment_card")
@Data
public class PaymentCard {
    @Id
    @Column(name="id")
    String id;
    @Column(name="cardNumber")
    String cardNumber;
    @Column(name="expireYear")
    String expireYear;
    @Column(name="expireMoth")
    String expireMoth;
    @Column(name="email")
    String email;
}
