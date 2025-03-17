package org.jers.parameters.model.dto.state;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;

@NoArgsConstructor
@Getter
@Setter
@Dto
public class StateOutputDTO extends BaseEntityOutputDTO {
    private Integer idState;
    private String stateName;
    private String landlinePhoneIdentifier;
    private Integer idCountry;
}