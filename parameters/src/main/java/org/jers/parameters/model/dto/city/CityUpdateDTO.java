package org.jers.parameters.model.dto.city;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@NoArgsConstructor
@Getter
@Setter
@Dto
public class CityUpdateDTO extends CityCreateDTO {
    @Schema(defaultValue = "1", requiredMode=REQUIRED)
    private Integer idCity;
}