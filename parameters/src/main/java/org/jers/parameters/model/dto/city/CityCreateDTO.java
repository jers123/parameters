package org.jers.parameters.model.dto.city;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;
import org.jers.parameters.model.dto.BaseEntityInputDTO;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;
import static org.jers.parameters.utils.Constants.GENDER_NAME_NOT_BLANK;
import static org.jers.parameters.utils.Constants.GENDER_NAME_NOT_NULL;
import static org.jers.parameters.utils.Constants.GENDER_NAME_SIZE;
import static org.jers.parameters.utils.SystemConstants.GENDER_NAME_LENGTH;

@NoArgsConstructor
@Getter
@Setter
@Dto
public class CityCreateDTO extends BaseEntityInputDTO {
    //@NotNull(message = GENDER_NAME_NOT_NULL)
    //@NotBlank(message = GENDER_NAME_NOT_BLANK)
    //@Size(min = 1, max = GENDER_NAME_LENGTH, message = GENDER_NAME_SIZE)
    @Schema(defaultValue = "Zipaquirá", requiredMode=REQUIRED)
    private String cityName;

    @Schema(defaultValue = "1", requiredMode=REQUIRED)
    private Integer idState;

    @Schema(defaultValue = "1", requiredMode=NOT_REQUIRED)
    private String landlinePhoneIdentifier;

    @Schema(defaultValue = "10.0", requiredMode=NOT_REQUIRED)
    private Double minimumTemperature;

    @Schema(defaultValue = "25.0", requiredMode=NOT_REQUIRED)
    private Double maximumTemperature;

    @Schema(defaultValue = "2000.0", requiredMode=NOT_REQUIRED)
    private Double heightAboveSeaLevel;
}