package org.jers.models.ascenture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Franchise extends BaseEntity {
    @Id
    private Integer idFranchise;

    @Column(nullable = false, length = 50, unique = true)
    private String name;
}
