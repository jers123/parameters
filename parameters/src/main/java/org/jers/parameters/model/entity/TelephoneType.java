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

import static org.jers.parameters.utils.SystemConstants.DATABASE;
import static org.jers.parameters.utils.SystemConstants.ID;
import static org.jers.parameters.utils.SystemConstants.NAME;
import static org.jers.parameters.utils.SystemConstants.SCHEMA;
import static org.jers.parameters.utils.SystemConstants.TELEPHONE_TYPE_NAME_LENGTH;
import static org.jers.parameters.utils.SystemConstants.TELEPHONE_TYPE_TABLE;

@Entity
@Table(name = TELEPHONE_TYPE_TABLE, catalog = DATABASE, schema = SCHEMA,
        uniqueConstraints = {
                @UniqueConstraint(name = TELEPHONE_TYPE_TABLE + "_" + TELEPHONE_TYPE_TABLE + "_" + NAME + "_uk", columnNames = {TELEPHONE_TYPE_TABLE + "_" + NAME})
        }
)
@NoArgsConstructor
@Getter
@Setter
public class TelephoneType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID + "_" + TELEPHONE_TYPE_TABLE, nullable = false, updatable = false)
    private Integer idTelephoneType;

    @Column(name = TELEPHONE_TYPE_TABLE + "_" + NAME, nullable = false, length = TELEPHONE_TYPE_NAME_LENGTH, unique = true)
    private String telephoneTypeName;
}