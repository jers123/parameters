package org.castor.employee.utils.mappers;

import org.castor.employee.model.dto.BaseEntityDTO;
import org.castor.employee.model.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public interface IMapper<BC extends BaseEntityDTO, BUO extends BaseEntityDTO, BE extends BaseEntity> {
	BE create(BC entityDto);
	BE create(BC entityDto, BE entity);
	BUO read(BE entity);
	default List<BUO> readList(List<BE> entities) {
		List<BUO> entitiesDto = new ArrayList<>();
		for (BE entity : entities) {
			entitiesDto.add(read(entity));
		}
		return entitiesDto;
	}
	BE update(BUO entityDto, BE entity);
}