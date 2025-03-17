package org.jers.parameters.model.dto.country;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;

@NoArgsConstructor
@Getter
@Setter
@Dto
public class CountryOutputDTO extends BaseEntityOutputDTO {
    private Integer idCountry;
    private String countryName;
    private String phoneIdentifier;
}