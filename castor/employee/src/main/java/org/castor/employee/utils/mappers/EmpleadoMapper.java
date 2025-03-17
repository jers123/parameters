package org.castor.employee.utils.mappers;

import org.castor.employee.model.dto.empleado.EmpleadoCreateDTO;
import org.castor.employee.model.dto.empleado.EmpleadoUpdateDTO;
import org.castor.employee.model.entity.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper implements IMapper<EmpleadoCreateDTO, EmpleadoCreateDTO, Empleado> {

	@Override
	public Empleado create(EmpleadoCreateDTO entityDto) {
		Empleado entity = new Empleado();
		entity.setCedula(entityDto.getCedula());
		entity.setNombre(entityDto.getNombre());
		entity.setFoto(entityDto.getFoto());
		entity.setFechaIngreso(entityDto.getFechaIngreso());
		return entity;
	}
	
	@Override
	public Empleado create(EmpleadoCreateDTO entityDto, Empleado entity) {
		entity.setCedula(entityDto.getCedula());
		entity.setNombre(entityDto.getNombre());
		entity.setFoto(entityDto.getFoto());
		entity.setFechaIngreso(entityDto.getFechaIngreso());
		return entity;
	}

	@Override
	public EmpleadoUpdateDTO read(Empleado entity) {
		EmpleadoUpdateDTO entityDto = new EmpleadoUpdateDTO();
		entityDto.setIdEmpleado(entity.getIdEmpleado());
		entityDto.setCedula(entity.getCedula());
		entityDto.setNombre(entity.getNombre());
		entityDto.setFoto(entity.getFoto());
		entityDto.setFechaIngreso(entity.getFechaIngreso());
		entityDto.setIdCargo(entity.getIdCargo().getIdCargo());
		return entityDto;
	}

	@Override
	public Empleado update(EmpleadoCreateDTO entityDto, Empleado entity) {
		entity.setCedula(entityDto.getCedula());
		entity.setNombre(entityDto.getNombre());
		entity.setFoto(entityDto.getFoto());
		entity.setFechaIngreso(entityDto.getFechaIngreso());
		return entity;
	}
}