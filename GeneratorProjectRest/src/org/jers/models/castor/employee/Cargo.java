package org.jers.models.castor.employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Cargo extends BaseEntity {
    @Id
    private Integer idCargo;

    @Column(nullable = false, length = 20, unique = true)
    private String nombre;
}