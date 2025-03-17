package org.jers.parameters.utils.mappers;

import org.jers.parameters.model.dto.BaseEntityInputDTO;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;
import org.jers.parameters.model.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public interface IMapper<BEC extends BaseEntityInputDTO, BEU extends BaseEntityInputDTO, BEO extends BaseEntityOutputDTO, BE extends BaseEntity> {
    BE create(BEC entityDto);
    BEO read(BE entity);
    default List<BEO> readList(List<BE> entities) {
        List<BEO> entitiesDto = new ArrayList<>();
        for (BE entity : entities) {
            entitiesDto.add(read(entity));
        }
        return entitiesDto;
    }
    BE update(BEU entityDto, BE entity);
}