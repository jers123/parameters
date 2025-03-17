package org.jers.parameters.model.dto.telephonetype;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static org.jers.parameters.utils.SystemConstants.TELEPHONE_TYPE_NAME_LENGTH;

@NoArgsConstructor
@Getter
@Setter
@Dto
@Schema(name = "Telephone type Output", description = "Telephone type entity with all properties")
public class TelephoneTypeOutputDTO extends BaseEntityOutputDTO {
    @Schema(defaultValue = "1", minimum = "1", nullable = true, requiredMode=NOT_REQUIRED, description = "Telephone type identification on registries")
    private Integer idTelephoneType;

    @Schema(defaultValue = "Mobile", minLength = 1, maxLength = TELEPHONE_TYPE_NAME_LENGTH, nullable = true, requiredMode=NOT_REQUIRED, description = "Is the telephone type's name or title")
    private String telephoneTypeName;
}