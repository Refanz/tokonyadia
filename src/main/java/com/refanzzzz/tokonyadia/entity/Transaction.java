package com.refanzzzz.tokonyadia.entity;

import com.refanzzzz.tokonyadia.constant.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_transaction")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Transaction extends BaseEntity {

    @Column(name = "transaction_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transactionDate;

    @Column(name = "transaction_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @OneToOne(mappedBy = "transaction")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<TransactionDetail> transactionDetails;

    @PrePersist
    protected void prePersist() {
        this.transactionDate = LocalDateTime.now();
    }
}
