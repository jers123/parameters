package org.jers.parameters.utils.mappers;

import org.jers.parameters.model.dto.telephonetype.TelephoneTypeCreateDTO;
import org.jers.parameters.model.dto.telephonetype.TelephoneTypeOutputDTO;
import org.jers.parameters.model.dto.telephonetype.TelephoneTypeUpdateDTO;
import org.jers.parameters.model.entity.TelephoneType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TelephoneTypeMapper implements IMapper<TelephoneTypeCreateDTO, TelephoneTypeUpdateDTO, TelephoneTypeOutputDTO, TelephoneType> {

    @Override
    public TelephoneType create(TelephoneTypeCreateDTO entityDto) {
        TelephoneType entity = new TelephoneType();
        entity.setTelephoneTypeName(entityDto.getTelephoneTypeName());
        if(entityDto.getStatus() != null) {
            entity.setStatus(entityDto.getStatus());
        } else {
            entity.setStatus(true);
        }
        entity.setCreationDate(LocalDateTime.now());
        entity.setUpdateDate(entity.getCreationDate());
        return entity;
    }

    @Override
    public TelephoneTypeOutputDTO read(TelephoneType entity) {
        TelephoneTypeOutputDTO entityDto = new TelephoneTypeOutputDTO();
        entityDto.setIdTelephoneType(entity.getIdTelephoneType());
        entityDto.setTelephoneTypeName(entity.getTelephoneTypeName());
        entityDto.setStatus(entity.getStatus());
        entityDto.setCreationDate(entity.getCreationDate());
        entityDto.setUpdateDate(entity.getUpdateDate());
        return entityDto;
    }

    @Override
    public TelephoneType update(TelephoneTypeUpdateDTO entityDto, TelephoneType entity) {
        entity.setTelephoneTypeName(entityDto.getTelephoneTypeName());
        if(entityDto.getStatus() != null) {
            entity.setStatus(entityDto.getStatus());
        }
        entity.setUpdateDate(LocalDateTime.now());
        return entity;
    }
}