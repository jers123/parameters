package org.jers.parameters.model.dto.gender;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static org.jers.parameters.utils.SystemConstants.GENDER_NAME_LENGTH;

@NoArgsConstructor
@Getter
@Setter
@Dto
@Schema(name = "Gender Output", description = "Gender entity with all properties")
public class GenderOutputDTO extends BaseEntityOutputDTO {
    @Schema(defaultValue = "1", minimum = "1", nullable = true, requiredMode=NOT_REQUIRED, description = "Gender identification on registries")
    private Integer idGender;

    @Schema(defaultValue = "Others", minLength = 1, maxLength = GENDER_NAME_LENGTH, nullable = true, requiredMode=NOT_REQUIRED, description = "Is the gender's name or title")
    private String genderName;
}