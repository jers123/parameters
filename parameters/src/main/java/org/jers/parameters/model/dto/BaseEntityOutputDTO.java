package org.jers.parameters.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

@Getter
@Setter
@Schema(hidden = true)
public abstract class BaseEntityOutputDTO extends BaseEntityInputDTO {
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(defaultValue = "2024-06-16T13:40:55.333", nullable = true, requiredMode=NOT_REQUIRED, description = "Create date each registry")
    private LocalDateTime creationDate;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(defaultValue = "2024-06-16T13:40:55.333", nullable = true, requiredMode=NOT_REQUIRED, description = "Update date each registry")
    private LocalDateTime updateDate;
}