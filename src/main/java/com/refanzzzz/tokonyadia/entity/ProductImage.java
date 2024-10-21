package com.refanzzzz.tokonyadia.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "m_product_image")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class ProductImage extends File {
    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;
}
