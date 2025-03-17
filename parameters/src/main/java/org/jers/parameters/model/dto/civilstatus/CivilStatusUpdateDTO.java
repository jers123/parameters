package org.jers.parameters.model.dto.civilstatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;
import static org.jers.parameters.utils.Constants.ID_VALUE_MINIMUM;

@NoArgsConstructor
@Getter
@Setter
@Dto
@Schema(name = "Civil status update", description = "Civil status entity with all properties to update")
public class CivilStatusUpdateDTO extends CivilStatusCreateDTO {
    @Min(value = 1, message = ID_VALUE_MINIMUM)
    @Schema(defaultValue = "1", minimum = "1", requiredMode=REQUIRED, description = "Civil status identification on registries")
    private Integer idCivilStatus;
}