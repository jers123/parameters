package org.jers.parameters.model.dto.civilstatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;
import org.jers.parameters.model.dto.BaseEntityInputDTO;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;
import static org.jers.parameters.utils.Constants.CIVIL_STATUS_NAME_NOT_BLANK;
import static org.jers.parameters.utils.Constants.CIVIL_STATUS_NAME_NOT_NULL;
import static org.jers.parameters.utils.Constants.CIVIL_STATUS_NAME_SIZE;
import static org.jers.parameters.utils.SystemConstants.CIVIL_STATUS_NAME_LENGTH;

@NoArgsConstructor
@Getter
@Setter
@Dto
@Schema(name = "Civil status create", description = "Civil status entity with all new properties to save")
public class CivilStatusCreateDTO extends BaseEntityInputDTO {
    @NotNull(message = CIVIL_STATUS_NAME_NOT_NULL)
    @NotBlank(message = CIVIL_STATUS_NAME_NOT_BLANK)
    @Size(min = 1, max = CIVIL_STATUS_NAME_LENGTH, message = CIVIL_STATUS_NAME_SIZE)
    @Schema(defaultValue = "Single", minLength = 1, maxLength = CIVIL_STATUS_NAME_LENGTH, requiredMode=REQUIRED, description = "Is the civil status's name or title")
    private String civilStatusName;
}