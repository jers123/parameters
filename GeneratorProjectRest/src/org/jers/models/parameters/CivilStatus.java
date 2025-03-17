package org.jers.models.parameters;

import jakarta.persistence.*;

@Entity
public class CivilStatus extends BaseEntity {
    @Id
    private Integer idCivilStatus;

    @Column(nullable = false, length = 25, unique = true)
    private String civilStatusName;
}