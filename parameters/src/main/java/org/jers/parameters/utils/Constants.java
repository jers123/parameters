package org.jers.parameters.utils;

import static org.jers.parameters.utils.SystemConstants.CIVIL_STATUS_NAME_LENGTH;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_ACRONYM_LENGTH;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_NAME_LENGTH;
import static org.jers.parameters.utils.SystemConstants.GENDER_NAME_LENGTH;
import static org.jers.parameters.utils.SystemConstants.SECTOR_NAME_LENGTH;
import static org.jers.parameters.utils.SystemConstants.TELEPHONE_TYPE_NAME_LENGTH;

public class Constants {
    public static final String ID_VALUE_MINIMUM = "El ID ingresado debe ser mayor o igual a 1";
    public static final String NO_CONTENT = "No hay registros";
    public static final String NO_CONTENT_ID = "No hay registros con el id = ";
    public static final String YES_CONTENT = "Si hay registros";

    // ERRORS
    public static final String HTTP_MESSAGE1 = "Problema de método HTTP, se esperaba la petición por medio del método ";
    public static final String HTTP_MESSAGE2 = " pero se solicitó por medio del metodo ";

    // CIVIL STATUS
    public static final String CIVIL_STATUS_NAME_EXISTS = "El nombre del estado civil ya existe.";
    public static final String CIVIL_STATUS_NAME_NOT_BLANK = "El nombre del estado civil no puede ser vacío.";
    public static final String CIVIL_STATUS_NAME_NOT_NULL = "El nombre del estado civil no puede ser nulo.";
    public static final String CIVIL_STATUS_NAME_SIZE = "El tamaño del nombre del estado civil es mínimo de 1 y máximo de " + CIVIL_STATUS_NAME_LENGTH + ".";
    public static final String SUCCESSFULLY_CREATED_CIVIL_STATUS = "Estado civil creado exitosamente.";
    public static final String SUCCESSFULLY_DELETED_CIVIL_STATUS = "Estado civil eliminado exitosamente.";
    public static final String SUCCESSFULLY_UPDATED_CIVIL_STATUS = "Estado civil actualizado exitosamente.";

    // DOCUMENT TYPE
    public static final String DOCUMENT_TYPE_ACRONYM_EXISTS = "La sigla del tipo de documento ya existe.";
    public static final String DOCUMENT_TYPE_ACRONYM_NOT_BLANK = "La sigla del tipo de documento no puede ser vacía.";
    public static final String DOCUMENT_TYPE_ACRONYM_NOT_NULL = "La sigla del tipo de documento no puede ser nula.";
    public static final String DOCUMENT_TYPE_ACRONYM_SIZE = "El tamaño de la sigla del tipo de documento es mínimo de 1 y máximo de " + DOCUMENT_TYPE_ACRONYM_LENGTH + ".";
    public static final String DOCUMENT_TYPE_NAME_EXISTS = "El nombre del tipo de documento ya existe.";
    public static final String DOCUMENT_TYPE_NAME_NOT_BLANK = "El nombre del tipo de documento no puede ser vacío.";
    public static final String DOCUMENT_TYPE_NAME_NOT_NULL = "El nombre del tipo de documento no puede ser nulo.";
    public static final String DOCUMENT_TYPE_NAME_SIZE = "El tamaño del nombre del tipo de documento es mínimo de 1 y máximo de " + DOCUMENT_TYPE_NAME_LENGTH + ".";
    public static final String SUCCESSFULLY_CREATED_DOCUMENT_TYPE = "Tipo de documento creado exitosamente.";
    public static final String SUCCESSFULLY_DELETED_DOCUMENT_TYPE = "Tipo de documento eliminado exitosamente.";
    public static final String SUCCESSFULLY_UPDATED_DOCUMENT_TYPE = "Tipo de documento actualizado exitosamente.";

    // GENDER
    public static final String GENDER_NAME_EXISTS = "El nombre del género ya existe.";
    public static final String GENDER_NAME_NOT_BLANK = "El nombre del género no puede ser vacío.";
    public static final String GENDER_NAME_NOT_NULL = "El nombre del género no puede ser nulo.";
    public static final String GENDER_NAME_SIZE = "El tamaño del nombre del género es mínimo de 1 y máximo de " + GENDER_NAME_LENGTH + ".";
    public static final String SUCCESSFULLY_CREATED_GENDER = "Género creado exitosamente.";
    public static final String SUCCESSFULLY_DELETED_GENDER = "Género eliminado exitosamente.";
    public static final String SUCCESSFULLY_UPDATED_GENDER = "Género actualizado exitosamente.";

    // SECTOR
    public static final String SECTOR_NAME_EXISTS = "El nombre del sector ya existe.";
    public static final String SECTOR_NAME_NOT_BLANK = "El nombre del sector no puede ser vacío.";
    public static final String SECTOR_NAME_NOT_NULL = "El nombre del sector no puede ser nulo.";
    public static final String SECTOR_NAME_SIZE = "El tamaño del nombre del sector es mínimo de 1 y máximo de " + SECTOR_NAME_LENGTH + ".";
    public static final String SUCCESSFULLY_CREATED_SECTOR = "Sector creado exitosamente.";
    public static final String SUCCESSFULLY_DELETED_SECTOR = "Sector eliminado exitosamente.";
    public static final String SUCCESSFULLY_UPDATED_SECTOR = "Sector actualizado exitosamente.";

    // TELEPHONE TYPE
    public static final String SUCCESSFULLY_CREATED_TELEPHONE_TYPE = "Tipo de teléfono creado exitosamente.";
    public static final String SUCCESSFULLY_DELETED_TELEPHONE_TYPE = "Tipo de teléfono eliminado exitosamente.";
    public static final String SUCCESSFULLY_UPDATED_TELEPHONE_TYPE = "Tipo de teléfono actualizado exitosamente.";
    public static final String TELEPHONE_TYPE_NAME_EXISTS = "El nombre del tipo de teléfono ya existe.";
    public static final String TELEPHONE_TYPE_NAME_NOT_BLANK = "El nombre del tipo de teléfono no puede ser vacío.";
    public static final String TELEPHONE_TYPE_NAME_NOT_NULL = "El nombre del tipo de teléfono no puede ser nulo.";
    public static final String TELEPHONE_TYPE_NAME_SIZE = "El tamaño del nombre del tipo de teléfono es mínimo de 1 y máximo de " + TELEPHONE_TYPE_NAME_LENGTH + ".";



    /*private static final String entityNameNotNull(String entity) {
        return "El nombre de" + entity + " no puede ser nulo.";
    }

    private static final String entityNameNotBlank(String entity) {
        return "El nombre de" + entity + " no puede ser vacio.";
    }*/
}