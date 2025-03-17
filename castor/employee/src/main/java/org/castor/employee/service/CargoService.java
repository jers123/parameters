package org.castor.employee.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.castor.employee.model.dto.cargo.CargoCreateDTO;
import org.castor.employee.model.dto.cargo.CargoUpdateDTO;
import org.castor.employee.model.entity.Cargo;
import org.castor.employee.model.repository.ICargoRepository;
import org.castor.employee.utils.mappers.IMapper;
import org.castor.employee.utils.response.ReplyMessageList;
import org.castor.employee.utils.response.ReplyMessageSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static org.castor.employee.utils.Constants.CARGO_RELATION;
import static org.castor.employee.utils.Constants.NOMBRE_CARGO_EXISTS;
import static org.castor.employee.utils.Constants.NO_CONTENT;
import static org.castor.employee.utils.Constants.NO_CONTENT_ID;
import static org.castor.employee.utils.Constants.SUCCESSFULLY_CREATED_CARGO;
import static org.castor.employee.utils.Constants.SUCCESSFULLY_DELETED_CARGO;
import static org.castor.employee.utils.Constants.SUCCESSFULLY_UPDATED_CARGO;
import static org.castor.employee.utils.Constants.YES_CONTENT;
import static org.castor.employee.utils.SystemConstants.CARGO_PATH;
import static org.castor.employee.utils.SystemConstants.CREATE_PATH;
import static org.castor.employee.utils.SystemConstants.DELETE_PATH;
import static org.castor.employee.utils.SystemConstants.EMPLOYEE_PATH;
import static org.castor.employee.utils.SystemConstants.GET_ALL_PATH;
import static org.castor.employee.utils.SystemConstants.GET_ID_PATH;
import static org.castor.employee.utils.SystemConstants.UPDATE_PATH;

@Service
@Validated
public class CargoService implements IBaseService<CargoCreateDTO, CargoUpdateDTO> {

	@Autowired
	private ICargoRepository repository;

	@Autowired
	private ReplyMessageSimple<CargoUpdateDTO> replyMessageSimple;

	@Autowired
	private ReplyMessageList<CargoUpdateDTO> replyMessageList;

	@Autowired
	private IMapper<CargoCreateDTO, CargoUpdateDTO, Cargo> mapper;

	@Override
	public ReplyMessageSimple<CargoUpdateDTO> getCreate(CargoCreateDTO entityDto) {
		replyMessageSimple.setUri(getUri(CREATE_PATH));
		replyMessageSimple.setError(true);
		replyMessageSimple.setResponse(null);
		List<String> messages = new ArrayList<>();
		try {
			String nombre = repository.searchByNombre(0, entityDto.getNombre());
			if (nombre != null) {
				messages.add(NOMBRE_CARGO_EXISTS);
			}
			if (nombre == null) {
				Cargo entity = mapper.create(entityDto);
				CargoUpdateDTO entityOutput = mapper.read(repository.save(entity));
				replyMessageSimple.setHttpStatus(HttpStatus.CREATED);
				replyMessageSimple.setError(false);
				messages.add(SUCCESSFULLY_CREATED_CARGO);
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
	public ReplyMessageList<CargoUpdateDTO> getFindAll() {
		replyMessageList.setUri(getUri(GET_ALL_PATH));
		replyMessageList.setError(true);
		replyMessageList.setResponse(null);
		List<String> messages = new ArrayList<>();
		try {
			List<Cargo> entities = repository.searchAll();
			if (!entities.isEmpty()) {
				List<CargoUpdateDTO> entitiesDto = mapper.readList(entities);
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
	public ReplyMessageSimple<CargoUpdateDTO> getFindById(Integer id) {
		replyMessageSimple.setUri(getUri(GET_ID_PATH, id));
		replyMessageSimple.setError(true);
		replyMessageSimple.setResponse(null);
		List<String> messages = new ArrayList<>();
		try {
			Cargo entity = repository.findById(id).orElse(null);
			if (entity != null) {
				CargoUpdateDTO entityDto = mapper.read(entity);
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
	public ReplyMessageSimple<CargoUpdateDTO> getUpdate(CargoUpdateDTO entityDto) {
		replyMessageSimple.setUri(getUri(UPDATE_PATH));
		replyMessageSimple.setError(true);
		replyMessageSimple.setResponse(null);
		List<String> messages = new ArrayList<>();
		try {
			Cargo entity = repository.findById(entityDto.getIdCargo()).orElse(null);
			if (entity != null) {
				String nombre = repository.searchByNombre(entityDto.getIdCargo(), entityDto.getNombre());
				if (nombre != null) {
					messages.add(NOMBRE_CARGO_EXISTS);
				}
				if (nombre == null) {
					entity = mapper.update(entityDto, entity);
					CargoUpdateDTO entityOutput = mapper.read(repository.save(entity));
					replyMessageSimple.setHttpStatus(HttpStatus.CREATED);
					replyMessageSimple.setError(false);
					messages.add(SUCCESSFULLY_UPDATED_CARGO);
					replyMessageSimple.setResponse(entityOutput);
				} else {
					replyMessageSimple.setHttpStatus(HttpStatus.CONFLICT);
				}
			} else {
				replyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);
				messages.add(NO_CONTENT_ID + entityDto.getIdCargo());
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
	public ReplyMessageSimple<CargoUpdateDTO> getDelete(Integer id) {
		replyMessageSimple.setUri(getUri(DELETE_PATH, id));
		replyMessageSimple.setError(true);
		replyMessageSimple.setResponse(null);
		List<String> messages = new ArrayList<>();
		try {
			Cargo entity = repository.findById(id).orElse(null);
			if (entity != null) {
				if ((entity.getEmpleados() == null || entity.getEmpleados().isEmpty())) {
					repository.delete(entity);
					replyMessageSimple.setHttpStatus(HttpStatus.OK);
					replyMessageSimple.setError(false);
					messages.add(SUCCESSFULLY_DELETED_CARGO);
				} else {
					replyMessageSimple.setHttpStatus(HttpStatus.CONFLICT);
					messages.add(CARGO_RELATION);
				}
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
		return EMPLOYEE_PATH + CARGO_PATH + method;
	}

	@Override
	public String getUri(String method, Integer id) {
		return EMPLOYEE_PATH + CARGO_PATH + method + id;
	}

}