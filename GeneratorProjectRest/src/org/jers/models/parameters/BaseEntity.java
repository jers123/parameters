package org.jers.models.parameters;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @Column(nullable = false, insertable = false)
    private Boolean status;

    @Column(nullable = false, updatable = false, insertable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false, insertable = false)
    private LocalDateTime updateDate;
}