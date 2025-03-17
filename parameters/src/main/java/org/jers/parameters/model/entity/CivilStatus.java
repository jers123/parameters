package org.jers.parameters.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static org.jers.parameters.utils.SystemConstants.CIVIL_STATUS_NAME_LENGTH;
import static org.jers.parameters.utils.SystemConstants.CIVIL_STATUS_TABLE;
import static org.jers.parameters.utils.SystemConstants.DATABASE;
import static org.jers.parameters.utils.SystemConstants.ID;
import static org.jers.parameters.utils.SystemConstants.NAME;
import static org.jers.parameters.utils.SystemConstants.SCHEMA;

@Entity
@Table(name = CIVIL_STATUS_TABLE, catalog = DATABASE, schema = SCHEMA,
        uniqueConstraints = {
                @UniqueConstraint(name = CIVIL_STATUS_TABLE+ "_" + CIVIL_STATUS_TABLE + "_"+ NAME + "_uk", columnNames = {CIVIL_STATUS_TABLE + "_" + NAME})
        }
)
@NoArgsConstructor
@Getter
@Setter
public class CivilStatus extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID + "_" + CIVIL_STATUS_TABLE, nullable = false, updatable = false)
    private Integer idCivilStatus;

    @Column(name = CIVIL_STATUS_TABLE + "_" + NAME, nullable = false, length = CIVIL_STATUS_NAME_LENGTH, unique = true)
    private String civilStatusName;
}