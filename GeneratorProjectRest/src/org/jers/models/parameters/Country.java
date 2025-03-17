package org.jers.models.parameters;

import jakarta.persistence.*;

@Entity
public class Country extends BaseEntity {
    @Id
    private Integer idCountry;

    @Column(nullable = false, length = 100, unique = true)
    private String countryName;

    @Column(nullable = false, length = 5, unique = true)
    private String phoneIdentifier;
}