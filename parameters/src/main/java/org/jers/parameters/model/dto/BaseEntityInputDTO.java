package org.jers.parameters.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

@Getter
@Setter
@Schema(hidden = true)
public abstract class BaseEntityInputDTO {
    @Schema(defaultValue = "true", nullable = true, requiredMode=NOT_REQUIRED, description = "Status available or unavailable each registry")
    private Boolean status;
}