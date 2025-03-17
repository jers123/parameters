package org.jers.models.nexos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Position extends BaseEntity {
    @Id
    private Integer idPosition;

    @Column(nullable = false, length = 100, unique = true)
    private String positionName;
}