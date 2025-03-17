package org.jers.parameters.model.dto.civilstatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static org.jers.parameters.utils.SystemConstants.CIVIL_STATUS_NAME_LENGTH;

@NoArgsConstructor
@Getter
@Setter
@Dto
@Schema(name = "Civil status Output", description = "Civil status entity with all properties")
public class CivilStatusOutputDTO extends BaseEntityOutputDTO {
    @Schema(defaultValue = "1", minimum = "1", nullable = true, requiredMode=NOT_REQUIRED, description = "Civil status identification on registries")
    private Integer idCivilStatus;

    @Schema(defaultValue = "Single", minLength = 1, maxLength = CIVIL_STATUS_NAME_LENGTH, nullable = true, requiredMode=NOT_REQUIRED, description = "Is the civil status's name or title")
    private String civilStatusName;
}