package org.castor.employee.controller;

import org.castor.employee.annotation.RestApi;
import org.castor.employee.model.dto.empleado.EmpleadoCreateDTO;
import org.castor.employee.model.dto.empleado.EmpleadoUpdateDTO;
import org.castor.employee.service.IBaseService;
import org.castor.employee.utils.response.ReplyMessageList;
import org.castor.employee.utils.response.ReplyMessageSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.castor.employee.utils.SystemConstants.EMPLEADO_PATH;
import static org.castor.employee.utils.SystemConstants.EMPLOYEE_PATH;
import static org.castor.employee.utils.SystemConstants.answerList;
import static org.castor.employee.utils.SystemConstants.answerSimple;

@RestApi
@RequestMapping(path = EMPLOYEE_PATH + EMPLEADO_PATH)
public class EmpleadoController implements IBaseController<EmpleadoCreateDTO, EmpleadoUpdateDTO> {

	@Autowired
	private IBaseService<EmpleadoCreateDTO, EmpleadoUpdateDTO> service;

	@Override
	public ResponseEntity<ReplyMessageSimple> create(EmpleadoCreateDTO entityDto) {
		try {
			return answerSimple(service.getCreate(entityDto));
		} catch (DataAccessException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<ReplyMessageList> getAll() {
		try {
			return answerList(service.getFindAll());
		} catch (DataAccessException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<ReplyMessageSimple> getById(Integer id) {
		try {
			return answerSimple(service.getFindById(id));
		} catch (DataAccessException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<ReplyMessageSimple> update(EmpleadoUpdateDTO entityDto) {
		try {
			return answerSimple(service.getUpdate(entityDto));
		} catch (DataAccessException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<ReplyMessageSimple> delete(Integer id) {
		try {
			return answerSimple(service.getDelete(id));
		} catch (DataAccessException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}