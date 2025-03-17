package org.jers.models.parameters;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class StateType extends BaseEntity {
    @Id
    private Integer idStateType;

    @Column(nullable = false, length = 100, unique = true)
    private String stateTypeName;
}