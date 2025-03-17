package org.jers.parameters.model.dto.city;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;

@NoArgsConstructor
@Getter
@Setter
@Dto
public class CityOutputDTO extends BaseEntityOutputDTO {
    private Integer idGender;
    private String cityName;
    private Integer idState;
    private String landlinePhoneIdentifier;
    private Double minimumTemperature;
    private Double maximumTemperature;
    private Double heightAboveSeaLevel;
}