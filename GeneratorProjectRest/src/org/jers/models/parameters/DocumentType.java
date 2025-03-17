package org.jers.models.parameters;

import jakarta.persistence.*;

@Entity
public class DocumentType extends BaseEntity {
    @Id
    private Integer idDocumentType;

    @Column(nullable = false, length = 30, unique = true)
    private String documentTypeName;

    @Column(nullable = false, length = 5, unique = true)
    private String documentTypeAcronym;
}