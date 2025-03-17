package org.jers.models.ascenture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product extends BaseEntity {
    @Id
    private Integer idProduct;

    @Column(nullable = false, length = 20, unique = true)
    private String name;

    private Branch branch;
}
