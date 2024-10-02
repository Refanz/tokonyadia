package com.refanzzzz.tokonyadia.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_transaction_detail")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "price", nullable = false, columnDefinition = "bigint check(price > 0)")
    private Long price;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
