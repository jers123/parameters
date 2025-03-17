package org.jers.parameters.utils.mappers;

import org.jers.parameters.model.dto.sector.SectorCreateDTO;
import org.jers.parameters.model.dto.sector.SectorOutputDTO;
import org.jers.parameters.model.dto.sector.SectorUpdateDTO;
import org.jers.parameters.model.entity.Sector;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SectorMapper implements IMapper<SectorCreateDTO, SectorUpdateDTO, SectorOutputDTO, Sector> {

    @Override
    public Sector create(SectorCreateDTO entityDto) {
        Sector entity = new Sector();
        entity.setSectorName(entityDto.getSectorName());
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
    public SectorOutputDTO read(Sector entity) {
        SectorOutputDTO entityDto = new SectorOutputDTO();
        entityDto.setIdSector(entity.getIdSector());
        entityDto.setSectorName(entity.getSectorName());
        entityDto.setStatus(entity.getStatus());
        entityDto.setCreationDate(entity.getCreationDate());
        entityDto.setUpdateDate(entity.getUpdateDate());
        return entityDto;
    }

    @Override
    public Sector update(SectorUpdateDTO entityDto, Sector entity) {
        entity.setSectorName(entityDto.getSectorName());
        if(entityDto.getStatus() != null) {
            entity.setStatus(entityDto.getStatus());
        }
        entity.setUpdateDate(LocalDateTime.now());
        return entity;
    }
}