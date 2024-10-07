package com.refanzzzz.tokonyadia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "m_store")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "no_siup", nullable = false, length = 20, unique = true)
    private String noSiup;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(targetEntity = Product.class, mappedBy = "store", fetch = FetchType.LAZY)
    private List<Product> productList;
}