package org.jers.models.nexos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User extends BaseEntity {
    @Id
    private Integer idUser;

    @Column(nullable = false, length = 100, unique = true)
    private String userName;

    @Column(nullable = false)
    private Integer age;

    private Position idPosition;
}