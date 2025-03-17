package org.jers.models.castor.employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Empleado extends BaseEntity {
    @Id
    private Integer idEmpleado;

    @Column(nullable = false, unique = true)
    private Integer cedula;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 150, unique = true)
    private String foto;

    @Column(nullable = false)
    private LocalDate fechaIngreso;

    private Cargo idCargo;
}