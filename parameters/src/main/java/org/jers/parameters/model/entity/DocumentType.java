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

import static org.jers.parameters.utils.SystemConstants.ACRONYM;
import static org.jers.parameters.utils.SystemConstants.DATABASE;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_ACRONYM_LENGTH;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_NAME_LENGTH;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_TABLE;
import static org.jers.parameters.utils.SystemConstants.ID;
import static org.jers.parameters.utils.SystemConstants.NAME;
import static org.jers.parameters.utils.SystemConstants.SCHEMA;

@Entity
@Table(name = DOCUMENT_TYPE_TABLE, catalog = DATABASE, schema = SCHEMA,
        uniqueConstraints = {
                @UniqueConstraint(name = DOCUMENT_TYPE_TABLE + "_" + DOCUMENT_TYPE_TABLE + "_" + NAME + "_uk", columnNames = {DOCUMENT_TYPE_TABLE + "_" + NAME}),
                @UniqueConstraint(name = DOCUMENT_TYPE_TABLE + "_" + DOCUMENT_TYPE_TABLE + "_" + ACRONYM + "_uk", columnNames = {DOCUMENT_TYPE_TABLE + "_" + ACRONYM})
        }
)
@NoArgsConstructor
@Getter
@Setter
public class DocumentType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID + "_" + DOCUMENT_TYPE_TABLE, nullable = false, updatable = false)
    private Integer idDocumentType;

    @Column(name = DOCUMENT_TYPE_TABLE + "_" + NAME, nullable = false, length = DOCUMENT_TYPE_NAME_LENGTH, unique = true)
    private String documentTypeName;

    @Column(name = DOCUMENT_TYPE_TABLE + "_" + ACRONYM, nullable = false, length = DOCUMENT_TYPE_ACRONYM_LENGTH, unique = true)
    private String documentTypeAcronym;
}