package org.jers.models.ascenture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Branch extends BaseEntity {
    @Id
    private Integer idBranch;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    private Franchise franchise;
}
