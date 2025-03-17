package org.jers.models.parameters;

import jakarta.persistence.*;

@Entity
public class Gender extends BaseEntity {
    @Id
    private Integer idGender;

    @Column(nullable = false, length = 20, unique = true)
    private String genderName;
}