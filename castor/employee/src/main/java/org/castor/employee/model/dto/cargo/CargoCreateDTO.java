package org.castor.employee.model.dto.cargo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.castor.employee.model.dto.BaseEntityDTO;

import static org.castor.employee.utils.Constants.NOMBRE_CARGO_NOT_BLANK;
import static org.castor.employee.utils.Constants.NOMBRE_CARGO_NOT_NULL;
import static org.castor.employee.utils.Constants.NOMBRE_CARGO_SIZE;
import static org.castor.employee.utils.SystemConstants.NOMBRE_CARGO_LENGTH;

@Getter
@Setter
public class CargoCreateDTO extends BaseEntityDTO {
	@NotNull(message=NOMBRE_CARGO_NOT_NULL)
	@NotBlank(message=NOMBRE_CARGO_NOT_BLANK)
	@Size(min=1, max=NOMBRE_CARGO_LENGTH, message=NOMBRE_CARGO_SIZE)
	private String nombre;
}