package org.jers.parameters.utils.mappers;

import org.jers.parameters.model.dto.civilstatus.CivilStatusCreateDTO;
import org.jers.parameters.model.dto.civilstatus.CivilStatusOutputDTO;
import org.jers.parameters.model.dto.civilstatus.CivilStatusUpdateDTO;
import org.jers.parameters.model.entity.CivilStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CivilStatusMapper implements IMapper<CivilStatusCreateDTO, CivilStatusUpdateDTO, CivilStatusOutputDTO, CivilStatus> {
    @Override
    public CivilStatus create(CivilStatusCreateDTO entityDto) {
        CivilStatus entity = new CivilStatus();
        entity.setCivilStatusName(entityDto.getCivilStatusName());
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
    public CivilStatusOutputDTO read(CivilStatus entity) {
        CivilStatusOutputDTO entityDto = new CivilStatusOutputDTO();
        entityDto.setIdCivilStatus(entity.getIdCivilStatus());
        entityDto.setCivilStatusName(entity.getCivilStatusName());
        entityDto.setStatus(entity.getStatus());
        entityDto.setCreationDate(entity.getCreationDate());
        entityDto.setUpdateDate(entity.getUpdateDate());
        return entityDto;
    }

    @Override
    public CivilStatus update(CivilStatusUpdateDTO entityDto, CivilStatus entity) {
        entity.setCivilStatusName(entityDto.getCivilStatusName());
        if(entityDto.getStatus() != null) {
            entity.setStatus(entityDto.getStatus());
        }
        entity.setUpdateDate(LocalDateTime.now());
        return entity;
    }
}