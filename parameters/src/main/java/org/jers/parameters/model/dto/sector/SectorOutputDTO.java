package org.jers.parameters.model.dto.sector;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static org.jers.parameters.utils.SystemConstants.SECTOR_NAME_LENGTH;

@NoArgsConstructor
@Getter
@Setter
@Dto
@Schema(name = "Sector Output", description = "Sector entity with all properties")
public class SectorOutputDTO extends BaseEntityOutputDTO {
    @Schema(defaultValue = "1", minimum = "1", nullable = true, requiredMode=NOT_REQUIRED, description = "Sector identification on registries")
    private Integer idSector;

    @Schema(defaultValue = "Technology", minLength = 1, maxLength = SECTOR_NAME_LENGTH, nullable = true, requiredMode=NOT_REQUIRED, description = "Is the sector's name or title")
    private String sectorName;
}