package com.refanzzzz.tokonyadia.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_customer")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false, length = 15, unique = true)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @OneToOne
    private UserAccount userAccount;
}
