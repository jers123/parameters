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
import static org.jers.parameters.utils.SystemConstants.GENDER_NAME_LENGTH;
import static org.jers.parameters.utils.SystemConstants.GENDER_TABLE;
import static org.jers.parameters.utils.SystemConstants.ID;
import static org.jers.parameters.utils.SystemConstants.NAME;
import static org.jers.parameters.utils.SystemConstants.SCHEMA;

@Entity
@Table(name = GENDER_TABLE, catalog = DATABASE, schema = SCHEMA,
    uniqueConstraints = {
        @UniqueConstraint(name = GENDER_TABLE + "_" + GENDER_TABLE + "_" + NAME + "_uk", columnNames = {GENDER_TABLE + "_" + NAME})
    }
)
@NoArgsConstructor
@Getter
@Setter
public class Gender extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID + "_"+ GENDER_TABLE, nullable = false, updatable = false)
    private Integer idGender;

    @Column(name = GENDER_TABLE + "_" + NAME, nullable = false, length = GENDER_NAME_LENGTH, unique = true)
    private String genderName;
}