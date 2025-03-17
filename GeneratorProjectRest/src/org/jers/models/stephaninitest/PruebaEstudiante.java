package org.jers.models.stephaninitest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PruebaEstudiante {
    @Id
    private Integer eid;

    @Column(nullable = false, length = 20)
    private String nombre;

    @Column(nullable = false, length = 2)
    private String especialidad;

    @Column(nullable = false, length = 2)
    private String grado;
}
