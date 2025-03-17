package org.jers.models.parameters;

import jakarta.persistence.*;

@Entity
public class TelephoneType extends BaseEntity {
    @Id
    private Integer idTelephoneType;

    @Column(nullable = false, length = 25, unique = true)
    private String telephoneTypeName;
}