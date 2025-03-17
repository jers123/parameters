package org.jers.parameters.utils;

import org.jers.parameters.utils.response.ReplyMessageList;
import org.jers.parameters.utils.response.ReplyMessageSimple;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class SystemConstants {

    //SYSTEM
    public static final String API_KEY_VALUE = "${app.api-key}";

    // PATHS
    public static final String LOCAL_ORIGIN_PATH = "http://localhost:8001";
    public static final String PUBLIC_ORIGIN_PATH = "http://192.168.1.9:8001";
    public static final String LOCAL_ORIGIN_PATH2 = "http://localhost:8000";
    public static final String PUBLIC_ORIGIN_PATH2 = "http://192.168.1.9:8000";
    public static final String LOCAL_ORIGIN_ANGULAR_PATH = "http://localhost:4200";
    public static final String PUBLIC_ORIGIN_ANGULAR__PATH = "http://192.168.1.9:4200";
    public static final String PRIVATE_PC_ORIGIN_PATH = "http://JERS-2:8001";
    public static final String PUBLIC_PC_ORIGIN_PATH = "http://parameters.jers.org";
    public static final String PARAMETERS_PATH = "/parameters";
    public static final String CREATE_PATH = "/create";
    public static final String DELETE_PATH = "/delete/";
    public static final String GET_ALL_ACRONYM_PATH = "/get-all-acronyms";
    public static final String GET_ALL_PATH = "/get-all";
    public static final String GET_ID_PATH = "/get-id/";
    public static final String GET_STATUS_PATH = "/get-status/";
    public static final String GET_STATUS_ACRONYM_PATH = "/get-status-acronym/";
    public static final String UPDATE_PATH = "/update";

    //HEADERS
    public static final String ACCEPT = "Accept";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String LOCATION = "Location";
    public static final String X_API_KEY = "X-API-KEY";

    // SUBPATHS
    public static final String CIVIL_STATUS_PATH = "/civil-statuses";
    public static final String DOCUMENT_TYPE_PATH = "/document-types";
    public static final String GENDER_PATH = "/genders";
    public static final String SECTOR_PATH = "/sectors";
    public static final String TELEPHONE_TYPE_PATH = "/telephone-types";

    // DATABASE
    public static final String DATABASE = "parametersDB";
    public static final String SCHEMA = "public";

    //FIELDS
    public static final String ACRONYM = "acronym";
    public static final String CREATION_DATE = "creation_date";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String STATUS = "status";
    public static final String UPDATE_DATE = "update_date";

    // CIVIL STATUS
    public static final String CIVIL_STATUS_TABLE = "civil_status";
    public static final int CIVIL_STATUS_NAME_LENGTH = 25;
    public static final String CIVIL_STATUS_ALL_QUERY = "SELECT cs FROM CivilStatus cs ORDER BY cs.civilStatusName ASC";
    public static final String CIVIL_STATUS_ALL_STATUS_QUERY = "SELECT cs FROM CivilStatus cs WHERE cs.status = :" + STATUS + " ORDER BY cs.civilStatusName ASC";
    public static final String CIVIL_STATUS_NAME_QUERY = "SELECT cs.civilStatusName FROM CivilStatus cs WHERE LOWER(cs.civilStatusName) = LOWER(:" + NAME + ") AND cs.idCivilStatus != :" + ID;

    // DOCUMENT TYPE
    public static final String DOCUMENT_TYPE_TABLE = "document_type";
    public static final int DOCUMENT_TYPE_NAME_LENGTH = 30;
    public static final int DOCUMENT_TYPE_ACRONYM_LENGTH = 5;
    public static final String DOCUMENT_TYPE_ACRONYM_QUERY = "SELECT dt.documentTypeAcronym FROM DocumentType dt WHERE LOWER(dt.documentTypeAcronym) = LOWER(:" + ACRONYM + ") AND dt.idDocumentType != :" + ID;
    public static final String DOCUMENT_TYPE_ALL_QUERY_ACRONYM = "SELECT dt FROM DocumentType dt ORDER BY dt.documentTypeAcronym ASC";
    public static final String DOCUMENT_TYPE_ALL_QUERY_NAME = "SELECT dt FROM DocumentType dt ORDER BY dt.documentTypeName ASC";
    public static final String DOCUMENT_TYPE_ALL_STATUS_ACRONYM_QUERY = "SELECT dt FROM DocumentType dt WHERE dt.status = :" + STATUS + " ORDER BY dt.documentTypeAcronym ASC";
    public static final String DOCUMENT_TYPE_ALL_STATUS_NAME_QUERY = "SELECT dt FROM DocumentType dt WHERE dt.status = :" + STATUS + " ORDER BY dt.documentTypeName ASC";
    public static final String DOCUMENT_TYPE_NAME_QUERY = "SELECT dt.documentTypeName FROM DocumentType dt WHERE LOWER(dt.documentTypeName) = LOWER(:" + NAME + ") AND dt.idDocumentType != :" + ID;

    // GENDER
    public static final String GENDER_TABLE = "gender";
    public static final int GENDER_NAME_LENGTH = 20;
    public static final String GENDER_ALL_QUERY = "SELECT g FROM Gender g ORDER BY g.genderName ASC";
    public static final String GENDER_ALL_STATUS_QUERY = "SELECT g FROM Gender g WHERE g.status = :" + STATUS + " ORDER BY g.genderName ASC";
    public static final String GENDER_NAME_QUERY = "SELECT g.genderName FROM Gender g WHERE LOWER(g.genderName) = LOWER(:" + NAME + ") AND g.idGender != :" + ID;

    // SECTOR
    public static final String SECTOR_TABLE = "sector";
    public static final int SECTOR_NAME_LENGTH = 200;
    public static final String SECTOR_ALL_QUERY = "SELECT s FROM Sector s ORDER BY s.sectorName ASC";
    public static final String SECTOR_ALL_STATUS_QUERY = "SELECT s FROM Sector s WHERE s.status = :" + STATUS + " ORDER BY s.sectorName ASC";
    public static final String SECTOR_NAME_QUERY = "SELECT s.sectorName FROM Sector s WHERE LOWER(s.sectorName) = LOWER(:" + NAME + ") AND s.idSector != :" + ID;

    // TELEPHONE TYPE
    public static final String TELEPHONE_TYPE_TABLE = "telephone_type";
    public static final int TELEPHONE_TYPE_NAME_LENGTH = 25;
    public static final String TELEPHONE_TYPE_ALL_QUERY = "SELECT tt FROM TelephoneType tt ORDER BY tt.telephoneTypeName ASC";
    public static final String TELEPHONE_TYPE_ALL_STATUS_QUERY = "SELECT tt FROM TelephoneType tt WHERE tt.status = :" + STATUS + " ORDER BY tt.telephoneTypeName ASC";
    public static final String TELEPHONE_TYPE_NAME_QUERY = "SELECT tt.telephoneTypeName FROM TelephoneType tt WHERE LOWER(tt.telephoneTypeName) = LOWER(:" + NAME + ") AND tt.idTelephoneType != :" + ID;

    public static ResponseEntity<ReplyMessageSimple> answerSimple(ReplyMessageSimple replyMessage) {
        return ResponseEntity
                .status(replyMessage.getHttpStatus())
                .header(LOCATION, replyMessage.getUri())
                //.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .body(replyMessage);
    }

    public static ResponseEntity<ReplyMessageList> answerList(ReplyMessageList replyMessage) {
        return ResponseEntity
                .status(replyMessage.getHttpStatus())
                .header(LOCATION, replyMessage.getUri())
                //.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .body(replyMessage);
    }
}