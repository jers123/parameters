package org.jers.parameters.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jers.parameters.annotation.RestApi;
import org.jers.parameters.model.dto.civilstatus.CivilStatusCreateDTO;
import org.jers.parameters.model.dto.civilstatus.CivilStatusOutputDTO;
import org.jers.parameters.model.dto.civilstatus.CivilStatusUpdateDTO;
import org.jers.parameters.service.IBaseService;
import org.jers.parameters.utils.response.ReplyMessageList;
import org.jers.parameters.utils.response.ReplyMessageSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.jers.parameters.utils.Constants.CIVIL_STATUS_NAME_EXISTS;
import static org.jers.parameters.utils.Constants.NO_CONTENT;
import static org.jers.parameters.utils.Constants.NO_CONTENT_ID;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_CREATED_CIVIL_STATUS;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_DELETED_CIVIL_STATUS;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_UPDATED_CIVIL_STATUS;
import static org.jers.parameters.utils.Constants.YES_CONTENT;
import static org.jers.parameters.utils.SystemConstants.ACCEPT;
import static org.jers.parameters.utils.SystemConstants.AUTHORIZATION;
import static org.jers.parameters.utils.SystemConstants.CIVIL_STATUS_PATH;
import static org.jers.parameters.utils.SystemConstants.CONTENT_TYPE;
import static org.jers.parameters.utils.SystemConstants.PARAMETERS_PATH;
import static org.jers.parameters.utils.SystemConstants.answerList;
import static org.jers.parameters.utils.SystemConstants.answerSimple;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestApi
@RequestMapping(path = PARAMETERS_PATH + CIVIL_STATUS_PATH)
@Tag(name = "Civil status API", description = "Create, update, read and delete civil status")
public class CivilStatusController implements IBaseController<CivilStatusCreateDTO, CivilStatusUpdateDTO> {

    @Autowired
    private IBaseService<CivilStatusCreateDTO, CivilStatusUpdateDTO, CivilStatusOutputDTO> service;

    @Override
    @Operation(summary = "Create a new civil status" , description = "Create news registries of civil status")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = SUCCESSFULLY_CREATED_CIVIL_STATUS,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Civil status create", description = "Crete a civil status schema",
                                            implementation = CivilStatusOutputDTO.class)) }),
            @ApiResponse(responseCode = "409", description = CIVIL_STATUS_NAME_EXISTS,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", description = "Civil status can't created by a system error",
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageSimple> create(CivilStatusCreateDTO entityDto) {
        try {
            return answerSimple(service.getCreate(entityDto));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Show all registries of civil status", description = "Show all registries of civil status")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = YES_CONTENT + " OR " + NO_CONTENT,
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Civil status get all", description = "Get all civil status schema",
                                            implementation = CivilStatusOutputDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Can't get response by a system error",
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageList> getAll() {
        try {
            return answerList(service.getFindAll());
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Show a registry of civil status by id", description = "Show a registry of civil status by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = YES_CONTENT + " OR " + NO_CONTENT_ID,
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Civil status get all", description = "Get all civil status schema",
                                            implementation = CivilStatusOutputDTO.class)) }),
            @ApiResponse(responseCode = "404", description = NO_CONTENT_ID,
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", description = "Can't get response by a system error",
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageSimple> getById(//@Parameter(name = "Id civil status", example = "1", description = "ID of the civil status to be obtained", required = true)
                                                      Integer id) {
        try {
            return answerSimple(service.getFindById(id));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Show all registries of civil status by status", description = "Show all registries of civil status by status")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = YES_CONTENT,
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Civil status get all by status", description = "Get all civil status schema by status",
                                            implementation = CivilStatusOutputDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Can't get response by a system error",
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageList> getByStatus(//@Parameter(name = "Status civil status", example = "true", description = "Status of the civil status to be obtained", required = true)
                                                        Boolean status) {
        try {
            return answerList(service.getFindAllByStatus(status));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Update a registry of Civil status", description = "Update a registry of civil status by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = SUCCESSFULLY_UPDATED_CIVIL_STATUS,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Civil status update", description = "Update a civil status schema",
                                            implementation = CivilStatusOutputDTO.class)) }),
            @ApiResponse(responseCode = "404", description = NO_CONTENT_ID,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "409", description = CIVIL_STATUS_NAME_EXISTS,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", description = "Civil status can't updated by a system error",
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageSimple> update(CivilStatusUpdateDTO entityDto) {
        try {
            return answerSimple(service.getUpdate(entityDto));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Delete a registry of civil status", description = "Delete a registry of civil status by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = SUCCESSFULLY_DELETED_CIVIL_STATUS,
                    headers = {
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404", description = NO_CONTENT_ID,
                    headers = {
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", description = "Civil status can't deleted by a system error",
                    headers = {
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageSimple> delete(Integer id) {
        try {
            return answerSimple(service.getDelete(id));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}