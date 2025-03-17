package org.jers.parameters.model.dto.country;

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
import static org.jers.parameters.utils.Constants.GENDER_NAME_NOT_BLANK;
import static org.jers.parameters.utils.Constants.GENDER_NAME_NOT_NULL;
import static org.jers.parameters.utils.Constants.GENDER_NAME_SIZE;
import static org.jers.parameters.utils.SystemConstants.GENDER_NAME_LENGTH;

@NoArgsConstructor
@Getter
@Setter
@Dto
public class CountryCreateDTO extends BaseEntityInputDTO {
    //@NotNull(message = GENDER_NAME_NOT_NULL)
    //@NotBlank(message = GENDER_NAME_NOT_BLANK)
    //@Size(min = 1, max = GENDER_NAME_LENGTH, message = GENDER_NAME_SIZE)
    @Schema(defaultValue = "Colombia", requiredMode=REQUIRED)
    private String countryName;

    @Schema(defaultValue = "+57", requiredMode=REQUIRED)
    private String phoneIdentifier;
}