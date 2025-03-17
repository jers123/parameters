package org.castor.employee.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import static org.castor.employee.utils.SystemConstants.CARGO_TABLE;
import static org.castor.employee.utils.SystemConstants.DATABASE;
import static org.castor.employee.utils.SystemConstants.ID;
import static org.castor.employee.utils.SystemConstants.NOMBRE;
import static org.castor.employee.utils.SystemConstants.NOMBRE_CARGO_LENGTH;
import static org.castor.employee.utils.SystemConstants.SCHEMA;

@Entity
@Table(name = CARGO_TABLE, catalog = DATABASE, schema = SCHEMA,
	uniqueConstraints = {
		@UniqueConstraint(name = CARGO_TABLE + "_" + NOMBRE + "_uk", columnNames = {NOMBRE})
	}
)
@Getter
@Setter
public class Cargo extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = ID + "_" + CARGO_TABLE, nullable = false, updatable = false)
	private Integer idCargo;

	@Column(name=NOMBRE, nullable=false, length=NOMBRE_CARGO_LENGTH, unique=true)
	private String nombre;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idCargo", fetch = FetchType.LAZY)
	private List<Empleado> empleados;
}