package org.castor.employee.utils.mappers;

import org.castor.employee.model.dto.cargo.CargoCreateDTO;
import org.castor.employee.model.dto.cargo.CargoUpdateDTO;
import org.castor.employee.model.entity.Cargo;
import org.springframework.stereotype.Component;

@Component
public class CargoMapper implements IMapper<CargoCreateDTO, CargoUpdateDTO, Cargo> {

	@Override
	public Cargo create(CargoCreateDTO entityDto) {
		Cargo entity = new Cargo();
		entity.setNombre(entityDto.getNombre());
		return entity;
	}
	
	@Override
	public Cargo create(CargoCreateDTO entityDto, Cargo entity) {
		entity.setNombre(entityDto.getNombre());
		return entity;
	}

	@Override
	public CargoUpdateDTO read(Cargo entity) {
		CargoUpdateDTO entityDto = new CargoUpdateDTO();
		entityDto.setIdCargo(entity.getIdCargo());
		entityDto.setNombre(entity.getNombre());
		return entityDto;
	}

	@Override
	public Cargo update(CargoUpdateDTO entityDto, Cargo entity) {
		entity.setNombre(entityDto.getNombre());
		return entity;
	}
}