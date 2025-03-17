package org.castor.employee.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.castor.employee.model.dto.empleado.EmpleadoCreateDTO;
import org.castor.employee.model.dto.empleado.EmpleadoUpdateDTO;
import org.castor.employee.model.entity.Cargo;
import org.castor.employee.model.entity.Empleado;
import org.castor.employee.model.repository.ICargoRepository;
import org.castor.employee.model.repository.IEmpleadoRepository;
import org.castor.employee.utils.mappers.IMapper;
import org.castor.employee.utils.response.ReplyMessageList;
import org.castor.employee.utils.response.ReplyMessageSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static org.castor.employee.utils.Constants.CEDULA_EXISTS;
import static org.castor.employee.utils.Constants.FOTO_EXISTS;
import static org.castor.employee.utils.Constants.ID_CARGO_EMPLEADO_NOT_EXISTS;
import static org.castor.employee.utils.Constants.NO_CONTENT;
import static org.castor.employee.utils.Constants.NO_CONTENT_ID;
import static org.castor.employee.utils.Constants.SUCCESSFULLY_CREATED_EMPLEADO;
import static org.castor.employee.utils.Constants.SUCCESSFULLY_DELETED_EMPLEADO;
import static org.castor.employee.utils.Constants.SUCCESSFULLY_UPDATED_EMPLEADO;
import static org.castor.employee.utils.Constants.YES_CONTENT;
import static org.castor.employee.utils.SystemConstants.CREATE_PATH;
import static org.castor.employee.utils.SystemConstants.DELETE_PATH;
import static org.castor.employee.utils.SystemConstants.EMPLEADO_PATH;
import static org.castor.employee.utils.SystemConstants.EMPLOYEE_PATH;
import static org.castor.employee.utils.SystemConstants.GET_ALL_PATH;
import static org.castor.employee.utils.SystemConstants.GET_ID_PATH;
import static org.castor.employee.utils.SystemConstants.UPDATE_PATH;

@Service
@Validated
public class EmpleadoService implements IBaseService<EmpleadoCreateDTO, EmpleadoUpdateDTO> {

	@Autowired
	private IEmpleadoRepository repository;

	@Autowired
	private ICargoRepository CargoRepository;

	@Autowired
	private ReplyMessageSimple<EmpleadoUpdateDTO> replyMessageSimple;

	@Autowired
	private ReplyMessageList<EmpleadoUpdateDTO> replyMessageList;

	@Autowired
	private IMapper<EmpleadoCreateDTO, EmpleadoUpdateDTO, Empleado> mapper;

	@Override
	public ReplyMessageSimple<EmpleadoUpdateDTO> getCreate(EmpleadoCreateDTO entityDto) {
		replyMessageSimple.setUri(getUri(CREATE_PATH));
		replyMessageSimple.setError(true);
		replyMessageSimple.setResponse(null);
		List<String> messages = new ArrayList<>();
		try {
			Integer cedula = repository.searchByCedula(0, entityDto.getCedula());
			String foto = repository.searchByFoto(0, entityDto.getFoto());
			Cargo idCargo = CargoRepository.findById(entityDto.getIdCargo()).orElse(null);
			if (cedula != null) {
				messages.add(CEDULA_EXISTS);
			}
			if (foto != null) {
				messages.add(FOTO_EXISTS);
			}
			if (idCargo == null) {
				messages.add(ID_CARGO_EMPLEADO_NOT_EXISTS);
			}
			if (cedula == null && foto == null && idCargo != null) {
				Empleado entity = new Empleado();
				entity.setIdCargo(idCargo);
				entity = mapper.create(entityDto, entity);
				EmpleadoUpdateDTO entityOutput = mapper.read(repository.save(entity));
				replyMessageSimple.setHttpStatus(HttpStatus.CREATED);
				replyMessageSimple.setError(false);
				messages.add(SUCCESSFULLY_CREATED_EMPLEADO);
				replyMessageSimple.setResponse(entityOutput);
			} else {
				replyMessageSimple.setHttpStatus(HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			replyMessageSimple.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			messages.add(e.getMessage());
		}
		replyMessageSimple.setMessage(messages);
		replyMessageSimple.setDate(LocalDateTime.now());
		return replyMessageSimple;
	}

	@Override
	public ReplyMessageList<EmpleadoUpdateDTO> getFindAll() {
		replyMessageList.setUri(getUri(GET_ALL_PATH));
		replyMessageList.setError(true);
		replyMessageList.setResponse(null);
		List<String> messages = new ArrayList<>();
		try {
			List<Empleado> entities = repository.searchAll();
			if (!entities.isEmpty()) {
				List<EmpleadoUpdateDTO> entitiesDto = mapper.readList(entities);
				messages.add(YES_CONTENT);
				replyMessageList.setResponse(entitiesDto);
			} else {
				messages.add(NO_CONTENT);
			}
			replyMessageList.setHttpStatus(HttpStatus.OK);
			replyMessageList.setError(false);
		} catch (Exception e) {
			replyMessageList.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			messages.add(e.getMessage());
		}
		replyMessageList.setMessage(messages);
		replyMessageList.setDate(LocalDateTime.now());
		return replyMessageList;
	}

	@Override
	public ReplyMessageSimple<EmpleadoUpdateDTO> getFindById(Integer id) {
		replyMessageSimple.setUri(getUri(GET_ID_PATH, id));
		replyMessageSimple.setError(true);
		replyMessageSimple.setResponse(null);
		List<String> messages = new ArrayList<>();
		try {
			Empleado entity = repository.findById(id).orElse(null);
			if (entity != null) {
				EmpleadoUpdateDTO entityDto = mapper.read(entity);
				replyMessageSimple.setHttpStatus(HttpStatus.OK);
				replyMessageSimple.setError(false);
				messages.add(YES_CONTENT);
				replyMessageSimple.setResponse(entityDto);
			} else {
				replyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);
				messages.add(NO_CONTENT_ID + id);
			}
		} catch (Exception e) {
			replyMessageSimple.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			messages.add(e.getMessage());
		}
		replyMessageSimple.setMessage(messages);
		replyMessageSimple.setDate(LocalDateTime.now());
		return replyMessageSimple;
	}

	@Override
	public ReplyMessageSimple<EmpleadoUpdateDTO> getUpdate(EmpleadoUpdateDTO entityDto) {
		replyMessageSimple.setUri(getUri(UPDATE_PATH));
		replyMessageSimple.setError(true);
		replyMessageSimple.setResponse(null);
		List<String> messages = new ArrayList<>();
		try {
			Empleado entity = repository.findById(entityDto.getIdEmpleado()).orElse(null);
			if (entity != null) {
				Integer cedula = repository.searchByCedula(entityDto.getIdEmpleado(), entityDto.getCedula());
				String foto = repository.searchByFoto(entityDto.getIdEmpleado(), entityDto.getFoto());
				Cargo idCargo = CargoRepository.findById(entityDto.getIdCargo()).orElse(null);
				if (cedula != null) {
					messages.add(CEDULA_EXISTS);
				}
				if (foto != null) {
					messages.add(FOTO_EXISTS);
				}
				if (idCargo == null) {
					messages.add(ID_CARGO_EMPLEADO_NOT_EXISTS);
				}
				if (cedula == null && foto == null && idCargo != null) {
					entity.setIdCargo(idCargo);
					entity = mapper.update(entityDto, entity);
					EmpleadoUpdateDTO entityOutput = mapper.read(repository.save(entity));
					replyMessageSimple.setHttpStatus(HttpStatus.CREATED);
					replyMessageSimple.setError(false);
					messages.add(SUCCESSFULLY_UPDATED_EMPLEADO);
					replyMessageSimple.setResponse(entityOutput);
				} else {
					replyMessageSimple.setHttpStatus(HttpStatus.CONFLICT);
				}
			} else {
				replyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);
				messages.add(NO_CONTENT_ID + entityDto.getIdEmpleado());
			}
		} catch (Exception e) {
			replyMessageSimple.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			messages.add(e.getMessage());
		}
		replyMessageSimple.setMessage(messages);
		replyMessageSimple.setDate(LocalDateTime.now());
		return replyMessageSimple;
	}
	
	@Override
	public ReplyMessageSimple<EmpleadoUpdateDTO> getDelete(Integer id) {
		replyMessageSimple.setUri(getUri(DELETE_PATH, id));
		replyMessageSimple.setError(true);
		replyMessageSimple.setResponse(null);
		List<String> messages = new ArrayList<>();
		try {
			Empleado entity = repository.findById(id).orElse(null);
			if (entity != null) {
				repository.delete(entity);
				replyMessageSimple.setHttpStatus(HttpStatus.OK);
				replyMessageSimple.setError(false);
				messages.add(SUCCESSFULLY_DELETED_EMPLEADO);
			} else {
				replyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);
				messages.add(NO_CONTENT_ID + id);
			}
		} catch (Exception e) {
			replyMessageSimple.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			messages.add(e.getMessage());
		}
		replyMessageSimple.setMessage(messages);
		replyMessageSimple.setDate(LocalDateTime.now());
		return replyMessageSimple;
	}

	@Override
	public String getUri(String method) {
		return EMPLOYEE_PATH + EMPLEADO_PATH + method;
	}

	@Override
	public String getUri(String method, Integer id) {
		return EMPLOYEE_PATH + EMPLEADO_PATH + method + id;
	}
}