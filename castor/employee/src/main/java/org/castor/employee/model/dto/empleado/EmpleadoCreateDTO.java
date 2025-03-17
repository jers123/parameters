package org.castor.employee.model.dto.empleado;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.castor.employee.model.dto.BaseEntityDTO;

import static org.castor.employee.utils.Constants.CEDULA_NOT_NULL;
import static org.castor.employee.utils.Constants.FECHA_INGRESO_NOT_NULL;
import static org.castor.employee.utils.Constants.FOTO_NOT_BLANK;
import static org.castor.employee.utils.Constants.FOTO_NOT_NULL;
import static org.castor.employee.utils.Constants.FOTO_SIZE;
import static org.castor.employee.utils.Constants.ID_VALUE_MINIMUM;
import static org.castor.employee.utils.Constants.NOMBRE_EMPLEADO_NOT_BLANK;
import static org.castor.employee.utils.Constants.NOMBRE_EMPLEADO_NOT_NULL;
import static org.castor.employee.utils.Constants.NOMBRE_EMPLEADO_SIZE;
import static org.castor.employee.utils.SystemConstants.FOTO_LENGTH;
import static org.castor.employee.utils.SystemConstants.NOMBRE_EMPLEADO_LENGTH;

@Getter
@Setter
public class EmpleadoCreateDTO extends BaseEntityDTO {
	@NotNull(message=CEDULA_NOT_NULL)
	@Min(value=1, message=ID_VALUE_MINIMUM)
	private Integer cedula;
	
	@NotNull(message=NOMBRE_EMPLEADO_NOT_NULL)
	@NotBlank(message=NOMBRE_EMPLEADO_NOT_BLANK)
	@Size(min=1, max=NOMBRE_EMPLEADO_LENGTH, message=NOMBRE_EMPLEADO_SIZE)
	private String nombre;

	@NotNull(message=FOTO_NOT_NULL)
	@NotBlank(message=FOTO_NOT_BLANK)
	@Size(min=1, max=FOTO_LENGTH, message=FOTO_SIZE)
	private String foto;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message=FECHA_INGRESO_NOT_NULL)
	private LocalDate fechaIngreso;

	@Min(value=1, message=ID_VALUE_MINIMUM)
	private Integer idCargo;
}