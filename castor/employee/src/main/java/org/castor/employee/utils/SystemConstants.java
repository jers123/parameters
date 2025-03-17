package org.castor.employee.utils;

import org.castor.employee.utils.response.ReplyMessageList;
import org.castor.employee.utils.response.ReplyMessageSimple;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class SystemConstants {

	// PATHS
	public static final String LOCAL_ORIGIN_PATH = "http://localhost:8080";
	public static final String PUBLIC_ORIGIN_PATH = "http://localhost:4200";
	public static final String EMPLOYEE_PATH = "/employee";
	public static final String CREATE_PATH = "/create";
	public static final String DELETE_PATH = "/delete/";
	public static final String GET_ALL_PATH = "/get-all";
	public static final String GET_ID_PATH = "/get-id/";
	public static final String UPDATE_PATH = "/update";

	// HEADERS
	public static final String ACCEPT = "Accept";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String LOCATION = "Location";

	// SUBPATHS
	public static final String CARGO_PATH = "cargo";
	public static final String EMPLEADO_PATH = "empleado";

	// DATABASE
	public static final String DATABASE = "employeeDB";
	public static final String SCHEMA = "public";

	public static final String ID = "id";
	public static final String NOMBRE = "nombre";
	
	
	// CARGO
	public static final int NOMBRE_CARGO_LENGTH = 20;
	public static final String NOMBRE_CARGO_QUERY = "SELECT c.nombre FROM Cargo c WHERE LOWER(c.nombre) = LOWER(:" + NOMBRE + ") AND c.idCargo != :" + ID;
	public static final String CARGO_ALL_QUERY = "SELECT c FROM Cargo c ORDER BY c.nombre ASC";
	public static final String CARGO_TABLE = "cargo";


	// EMPLEADO
	public static final String CEDULA = "cedula";
	public static final int NOMBRE_EMPLEADO_LENGTH = 100;
	public static final String FOTO = "foto";
	public static final int FOTO_LENGTH = 150;
	public static final String FECHA_INGRESO = "fecha_ingreso";
	public static final String CEDULA_QUERY = "SELECT e.cedula FROM Empleado e WHERE e.cedula = :" + CEDULA + " AND e.idEmpleado != :" + ID;
	public static final String FOTO_QUERY = "SELECT e.foto FROM Empleado e WHERE LOWER(e.foto) = LOWER(:" + FOTO + ") AND e.idEmpleado != :" + ID;
	public static final String EMPLEADO_ID_CARGO_QUERY = "SELECT e FROM Empleado e WHERE e.idCargo = :" + ID + " ORDER BY e.cedula ASC";
	public static final String EMPLEADO_ALL_QUERY = "SELECT e FROM Empleado e ORDER BY e.cedula ASC";
	public static final String EMPLEADO_TABLE = "empleado";

	public static ResponseEntity<ReplyMessageSimple> answerSimple(ReplyMessageSimple replyMessage) {
		return ResponseEntity
				.status(replyMessage.getHttpStatus())
				.header(LOCATION, replyMessage.getUri())
				.header(ACCEPT, APPLICATION_JSON_VALUE)
				.body(replyMessage);
	}

	public static ResponseEntity<ReplyMessageList> answerList(ReplyMessageList replyMessage) {
		return ResponseEntity
				.status(replyMessage.getHttpStatus())
				.header(LOCATION, replyMessage.getUri())
				.header(ACCEPT, APPLICATION_JSON_VALUE)
				.body(replyMessage);
	}
}