package org.jers.parameters.model.dto.telephonetype;

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
import static org.jers.parameters.utils.Constants.TELEPHONE_TYPE_NAME_NOT_BLANK;
import static org.jers.parameters.utils.Constants.TELEPHONE_TYPE_NAME_NOT_NULL;
import static org.jers.parameters.utils.Constants.TELEPHONE_TYPE_NAME_SIZE;
import static org.jers.parameters.utils.SystemConstants.TELEPHONE_TYPE_NAME_LENGTH;

@NoArgsConstructor
@Getter
@Setter
@Dto
@Schema(name = "Telephone type create", description = "Telephone type entity with all new properties to save")
public class TelephoneTypeCreateDTO extends BaseEntityInputDTO {
    @NotBlank(message = TELEPHONE_TYPE_NAME_NOT_BLANK)
    @NotNull(message = TELEPHONE_TYPE_NAME_NOT_NULL)
    @Size(min = 1, max = TELEPHONE_TYPE_NAME_LENGTH, message = TELEPHONE_TYPE_NAME_SIZE)
    @Schema(defaultValue = "Mobile", minLength = 1, maxLength = TELEPHONE_TYPE_NAME_LENGTH, requiredMode=REQUIRED, description = "Is the telephone type's name or title")
    private String telephoneTypeName;
}