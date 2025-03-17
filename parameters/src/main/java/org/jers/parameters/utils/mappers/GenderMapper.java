package org.jers.parameters.utils.mappers;

import org.jers.parameters.model.dto.gender.GenderCreateDTO;
import org.jers.parameters.model.dto.gender.GenderOutputDTO;
import org.jers.parameters.model.dto.gender.GenderUpdateDTO;
import org.jers.parameters.model.entity.Gender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GenderMapper implements IMapper<GenderCreateDTO, GenderUpdateDTO, GenderOutputDTO, Gender> {

    @Override
    public Gender create(GenderCreateDTO entityDto) {
        Gender entity = new Gender();
        entity.setGenderName(entityDto.getGenderName());
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
    public GenderOutputDTO read(Gender entity) {
        GenderOutputDTO entityDto = new GenderOutputDTO();
        entityDto.setIdGender(entity.getIdGender());
        entityDto.setGenderName(entity.getGenderName());
        entityDto.setStatus(entity.getStatus());
        entityDto.setCreationDate(entity.getCreationDate());
        entityDto.setUpdateDate(entity.getUpdateDate());
        return entityDto;
    }

    @Override
    public Gender update(GenderUpdateDTO entityDto, Gender entity) {
        entity.setGenderName(entityDto.getGenderName());
        if(entityDto.getStatus() != null) {
            entity.setStatus(entityDto.getStatus());
        }
        entity.setUpdateDate(LocalDateTime.now());
        return entity;
    }
}