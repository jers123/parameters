package org.jers.parameters.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static org.jers.parameters.utils.SystemConstants.CREATION_DATE;
import static org.jers.parameters.utils.SystemConstants.DATABASE;
import static org.jers.parameters.utils.SystemConstants.ID;
import static org.jers.parameters.utils.SystemConstants.NAME;
import static org.jers.parameters.utils.SystemConstants.SCHEMA;
import static org.jers.parameters.utils.SystemConstants.STATUS;
import static org.jers.parameters.utils.SystemConstants.UPDATE_DATE;

@Entity
@Table(name = "country", catalog = DATABASE, schema = SCHEMA,
        uniqueConstraints = {
                @UniqueConstraint(name = "country_country_" + NAME + "_uk", columnNames = {"country_" + NAME}),
                @UniqueConstraint(name = "country_phone_identifier_uk", columnNames = {"phone_identifier"})
        }
)
@NoArgsConstructor
@Getter
@Setter
public class Country extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID + "_country", nullable = false, updatable = false)
    private Integer idCountry;

    @Column(name = "country_" + NAME, nullable = false, length = 100, unique = true)
    private String countryName;

    @Column(name = "phone_identifier", nullable = false, length = 5, unique = true)
    private String phoneIdentifier;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCountry", fetch = FetchType.LAZY)
    private List<State> states;
}