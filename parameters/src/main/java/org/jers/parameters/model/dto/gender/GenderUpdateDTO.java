package org.jers.parameters.model.dto.gender;

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
@Schema(name = "Gender update", description = "Gender entity with all properties to update")
public class GenderUpdateDTO extends GenderCreateDTO {
    @Min(value = 1, message = ID_VALUE_MINIMUM)
    @Schema(defaultValue = "1", minimum = "1", requiredMode=REQUIRED, description = "Gender identification on registries")
    private Integer idGender;
}