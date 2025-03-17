package org.castor.employee.model.dto.empleado;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import static org.castor.employee.utils.Constants.ID_VALUE_MINIMUM;

@Getter
@Setter
public class EmpleadoUpdateDTO extends EmpleadoCreateDTO {
	@Min(value=1, message=ID_VALUE_MINIMUM)
	private Integer idEmpleado;
}