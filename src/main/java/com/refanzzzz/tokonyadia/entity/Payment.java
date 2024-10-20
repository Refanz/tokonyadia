package com.refanzzzz.tokonyadia.entity;

import com.refanzzzz.tokonyadia.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "t_payment")
@SuperBuilder
public class Payment extends BaseEntity {

    @Column(name = "amount")
    private Long amount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "token_snap")
    private String tokenSnap;

    @Column(name = "redirect_url")
    private String redirectUrl;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
