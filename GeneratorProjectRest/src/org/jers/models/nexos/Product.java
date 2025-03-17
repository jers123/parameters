package org.jers.models.nexos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Product extends BaseEntity {
    @Id
    private Integer idProduct;

    @Column(nullable = false, length = 200, unique = true)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    private User idUserCreate;

    private User idUserUpdate;

    @Column(nullable = false)
    private LocalDate updateDate;
}