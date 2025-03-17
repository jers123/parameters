package org.castor.employee.utils;

import static org.castor.employee.utils.SystemConstants.FOTO_LENGTH;
import static org.castor.employee.utils.SystemConstants.NOMBRE_CARGO_LENGTH;
import static org.castor.employee.utils.SystemConstants.NOMBRE_EMPLEADO_LENGTH;

public class Constants {

	// ERRORS
	public static final String HTTP_MESSAGE1 = "Problema de método HTTP, se esperaba la petición por medio del método ";
	public static final String HTTP_MESSAGE2 = " pero se solicitó por medio del metodo ";

	public static final String ID_VALUE_MINIMUM = "El ID ingresado debe ser mayor o igual a 1";
	public static final String NO_CONTENT = "No hay registros";
	public static final String NO_CONTENT_ID = "No hay registros con el id = ";
	public static final String YES_CONTENT = "Si hay registros";

	// CARGO
	public static final String CARGO_RELATION = "No se puede eliminar el cargo porque tiene relación con otras tablas.";
	public static final String NOMBRE_CARGO_EXISTS = "El nombre del cargo ya existe.";
	public static final String NOMBRE_CARGO_NOT_BLANK = "El nombre del cargo no puede ser vacío.";
	public static final String NOMBRE_CARGO_NOT_NULL = "El nombre del Cargo no puede ser nulo.";
	public static final String NOMBRE_CARGO_SIZE = "El tamaño del nombre del cargo es mínimo de 1 y máximo de " + NOMBRE_CARGO_LENGTH + ".";
	public static final String SUCCESSFULLY_CREATED_CARGO = "Cargo creado exitosamente.";
	public static final String SUCCESSFULLY_DELETED_CARGO = "Cargo eliminado exitosamente.";
	public static final String SUCCESSFULLY_UPDATED_CARGO = "Cargo actualizado exitosamente.";


	// EMPLEADO
	public static final String CEDULA_EXISTS = "La cedula del Empleado ya existe.";
	public static final String CEDULA_NOT_NULL = "El cedula del Empleado no puede ser nulo.";
	public static final String FECHA_INGRESO_NOT_NULL = "El fechaIngreso del Empleado no puede ser nulo.";
	public static final String FOTO_EXISTS = "La foto del empleado ya existe.";
	public static final String FOTO_NOT_BLANK = "La foto del empleado no puede ser vacío.";
	public static final String FOTO_NOT_NULL = "La foto del empleado no puede ser nulo.";
	public static final String FOTO_SIZE = "El tamaño del foto del Empleado es mínimo de 1 y máximo de " + FOTO_LENGTH + ".";
	public static final String ID_CARGO_EMPLEADO_NOT_EXISTS = "El id del cargo no existe en la tabla Cargo.";
	public static final String NOMBRE_EMPLEADO_NOT_BLANK = "El nombre del Empleado no puede ser vacío.";
	public static final String NOMBRE_EMPLEADO_NOT_NULL = "El nombre del Empleado no puede ser nulo.";
	public static final String NOMBRE_EMPLEADO_SIZE = "El tamaño del nombre del Empleado es mínimo de 1 y máximo de " + NOMBRE_EMPLEADO_LENGTH + ".";
	public static final String SUCCESSFULLY_CREATED_EMPLEADO = "Empleado creado exitosamente.";
	public static final String SUCCESSFULLY_DELETED_EMPLEADO = "Empleado eliminado exitosamente.";
	public static final String SUCCESSFULLY_UPDATED_EMPLEADO = "Empleado actualizado exitosamente.";

}