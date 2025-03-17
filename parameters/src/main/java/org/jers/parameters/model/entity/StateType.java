package org.jers.parameters.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static org.jers.parameters.utils.SystemConstants.*;
import static org.jers.parameters.utils.SystemConstants.NAME;

@Entity
@Table(name = "city_type", catalog = DATABASE, schema = SCHEMA,
        uniqueConstraints = {
                @UniqueConstraint(name = "city_type" + "_" + "city_type" + "_" + NAME + "_uk", columnNames = {"city_type" + "_" + NAME})
        }
)
@NoArgsConstructor
@Getter
@Setter
public class StateType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID + "_state_type",nullable = false, updatable = false)
    private Integer idStateType;

    @Column(name = "state_type_name", nullable = false, length = 100, unique = true)
    private String stateTypeName;
}