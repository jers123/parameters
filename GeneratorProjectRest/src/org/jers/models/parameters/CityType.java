package org.jers.models.parameters;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CityType extends BaseEntity {
    @Id
    private Integer idCityType;

    @Column(nullable = false, length = 20, unique = true)
    private String cityTypeName;
}