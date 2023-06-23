package com.maersk.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="t_payment_card")
@Data
public class PaymentCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Long id;
    @Column(name="idCard")
    String idCard;
    @Column(name="cardNumber")
    String cardNumber;
    @Column(name="expireYear")
    String expireYear;
    @Column(name="expireMoth")
    String expireMoth;
    @Column(name="email")
    String email;
}
