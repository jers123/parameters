package org.jers.models.parameters;

import jakarta.persistence.*;

@Entity
public class State extends BaseEntity {
    @Id
    private Integer idState;

    @Column(nullable = false, length = 100)
    private String stateName;

    private Country idCountry;

    private StateType idStateType;

    @Column(length = 5)
    private String landlinePhoneIdentifier;
}