package org.castor.employee.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import static org.castor.employee.utils.SystemConstants.CARGO_TABLE;
import static org.castor.employee.utils.SystemConstants.CEDULA;
import static org.castor.employee.utils.SystemConstants.DATABASE;
import static org.castor.employee.utils.SystemConstants.EMPLEADO_TABLE;
import static org.castor.employee.utils.SystemConstants.FECHA_INGRESO;
import static org.castor.employee.utils.SystemConstants.FOTO;
import static org.castor.employee.utils.SystemConstants.FOTO_LENGTH;
import static org.castor.employee.utils.SystemConstants.ID;
import static org.castor.employee.utils.SystemConstants.NOMBRE;
import static org.castor.employee.utils.SystemConstants.NOMBRE_EMPLEADO_LENGTH;
import static org.castor.employee.utils.SystemConstants.SCHEMA;

@Entity
@Table(name = EMPLEADO_TABLE, catalog = DATABASE, schema = SCHEMA,
	uniqueConstraints = {
		@UniqueConstraint(name = EMPLEADO_TABLE + "_" + CEDULA + "_uk", columnNames = {CEDULA}),
		@UniqueConstraint(name = EMPLEADO_TABLE + "_" + FOTO + "_uk", columnNames = {FOTO})
	}
)
@Getter
@Setter
public class Empleado extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = EMPLEADO_TABLE + "_" + ID, nullable = false, updatable = false)
	private Integer idEmpleado;

	@Column(name=CEDULA, nullable=false, unique=true)
	private Integer cedula;

	@Column(name=NOMBRE, nullable=false, length=NOMBRE_EMPLEADO_LENGTH)
	private String nombre;

	@Column(name=FOTO, nullable=false, length=FOTO_LENGTH, unique=true)
	private String foto;

	@Column(name=FECHA_INGRESO, nullable=false)
	private LocalDate fechaIngreso;

	@JoinColumn(name = ID + "_" + CARGO_TABLE, referencedColumnName = ID + "_" + CARGO_TABLE, nullable = false,
		foreignKey = @ForeignKey(name = EMPLEADO_TABLE + "_" + CARGO_TABLE + "_" + ID + "_" + CARGO_TABLE + "_fk"))
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Cargo idCargo;
}