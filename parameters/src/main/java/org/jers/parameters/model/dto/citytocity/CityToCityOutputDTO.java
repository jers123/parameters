package org.jers.parameters.model.dto.citytocity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;

@NoArgsConstructor
@Getter
@Setter
@Dto
public class CityToCityOutputDTO extends BaseEntityOutputDTO {
    private Integer idCityToCity;
    private Integer idOriginCity;
    private Integer idDestinationCity;
    private Double distance;
    private int routeNumbers;
}