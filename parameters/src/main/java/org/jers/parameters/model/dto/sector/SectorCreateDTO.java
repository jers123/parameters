package org.jers.parameters.model.dto.sector;

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
import static org.jers.parameters.utils.Constants.SECTOR_NAME_NOT_BLANK;
import static org.jers.parameters.utils.Constants.SECTOR_NAME_NOT_NULL;
import static org.jers.parameters.utils.Constants.SECTOR_NAME_SIZE;
import static org.jers.parameters.utils.SystemConstants.SECTOR_NAME_LENGTH;

@NoArgsConstructor
@Getter
@Setter
@Dto
@Schema(name = "Sector create", description = "Sector entity with all new properties to save")
public class SectorCreateDTO extends BaseEntityInputDTO {
    @NotNull(message = SECTOR_NAME_NOT_NULL)
    @NotBlank(message = SECTOR_NAME_NOT_BLANK)
    @Size(min = 1, max = SECTOR_NAME_LENGTH, message = SECTOR_NAME_SIZE)
    @Schema(defaultValue = "Technology", minLength = 1, maxLength = SECTOR_NAME_LENGTH, requiredMode=REQUIRED, description = "Is the sector's name or title")
    private String sectorName;
}