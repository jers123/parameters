package org.jers.models.parameters;

import jakarta.persistence.*;

@Entity
public class Sector extends BaseEntity {
    @Id
    private Integer idSector;

    @Column(nullable = false, length = 200, unique = true)
    private String sectorName;
}