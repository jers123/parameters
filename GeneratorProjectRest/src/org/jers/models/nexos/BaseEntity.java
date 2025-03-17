package org.jers.models.nexos;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

@MappedSuperclass
public class BaseEntity {
    @Column(nullable = false)
    private LocalDate entryDate;
}