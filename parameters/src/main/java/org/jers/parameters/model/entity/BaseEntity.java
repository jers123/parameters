package org.jers.parameters.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

import static org.jers.parameters.utils.SystemConstants.CREATION_DATE;
import static org.jers.parameters.utils.SystemConstants.STATUS;
import static org.jers.parameters.utils.SystemConstants.UPDATE_DATE;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {
    @Column(name = STATUS, nullable = false)
    private Boolean status;

    @Column(name = CREATION_DATE, nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = UPDATE_DATE, nullable = false)
    private LocalDateTime updateDate;
}