package org.jers.parameters.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jers.parameters.annotation.RestApi;
import org.jers.parameters.model.dto.telephonetype.TelephoneTypeCreateDTO;
import org.jers.parameters.model.dto.telephonetype.TelephoneTypeOutputDTO;
import org.jers.parameters.model.dto.telephonetype.TelephoneTypeUpdateDTO;
import org.jers.parameters.service.IBaseService;
import org.jers.parameters.utils.response.ReplyMessageList;
import org.jers.parameters.utils.response.ReplyMessageSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.jers.parameters.utils.Constants.NO_CONTENT;
import static org.jers.parameters.utils.Constants.NO_CONTENT_ID;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_CREATED_TELEPHONE_TYPE;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_DELETED_TELEPHONE_TYPE;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_UPDATED_TELEPHONE_TYPE;
import static org.jers.parameters.utils.Constants.TELEPHONE_TYPE_NAME_EXISTS;
import static org.jers.parameters.utils.Constants.YES_CONTENT;
import static org.jers.parameters.utils.SystemConstants.ACCEPT;
import static org.jers.parameters.utils.SystemConstants.AUTHORIZATION;
import static org.jers.parameters.utils.SystemConstants.CONTENT_TYPE;
import static org.jers.parameters.utils.SystemConstants.PARAMETERS_PATH;
import static org.jers.parameters.utils.SystemConstants.TELEPHONE_TYPE_PATH;
import static org.jers.parameters.utils.SystemConstants.answerList;
import static org.jers.parameters.utils.SystemConstants.answerSimple;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestApi
@RequestMapping(path = PARAMETERS_PATH + TELEPHONE_TYPE_PATH)
@Tag(name = "TelephoneType API", description = "Create, update, read and delete telephone types")
public class TelephoneTypeController implements IBaseController<TelephoneTypeCreateDTO, TelephoneTypeUpdateDTO> {

    @Autowired
    private IBaseService<TelephoneTypeCreateDTO, TelephoneTypeUpdateDTO, TelephoneTypeOutputDTO> service;

    @Override
    @Operation(summary = "Create a new telephone type", description = "Create news registries of telephone types")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = SUCCESSFULLY_CREATED_TELEPHONE_TYPE,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Telephone type create", description = "Crete a telephone type schema",
                                            implementation = TelephoneTypeOutputDTO.class))}),
            @ApiResponse(responseCode = "409", description = TELEPHONE_TYPE_NAME_EXISTS,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", description = "Telephone type can't created by a system error",
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageSimple> create(TelephoneTypeCreateDTO entityDto) {
        try {
            return answerSimple(service.getCreate(entityDto));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Show all registries of telephone types", description = "Show all registries of telephone types")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = YES_CONTENT + " OR " + NO_CONTENT,
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Telephone type get all", description = "Get all telephone type schema",
                                            implementation = TelephoneTypeOutputDTO.class)) }),
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
    @Operation(summary = "Show a registry of telephone type by id", description = "Show a registry of telephone type by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = YES_CONTENT + " OR " + NO_CONTENT_ID,
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Telephone type get all", description = "Get all telephone type schema",
                                            implementation = TelephoneTypeOutputDTO.class)) }),
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
    public ResponseEntity<ReplyMessageSimple> getById(//@Parameter(name = "Id telephone type", example = "1", description = "ID of the gender to be obtained", required = true)
                                                      Integer id) {
        try {
            return answerSimple(service.getFindById(id));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Show all registries of telephone type by status", description = "Show all registries of telephone type by status")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = YES_CONTENT,
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Telephone type get all by status", description = "Get all telephone type schema by status",
                                            implementation = TelephoneTypeOutputDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Can't get response by a system error",
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageList> getByStatus(//@Parameter(name = "Status telephone type", example = "true", description = "Status of the telephone type to be obtained", required = true)
                                                        Boolean status) {
        try {
            return answerList(service.getFindAllByStatus(status));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Update a registry of telephone type", description = "Update a registry of telephone type by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = SUCCESSFULLY_UPDATED_TELEPHONE_TYPE,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Telephone type update", description = "Crete a telephone type schema",
                                            implementation = TelephoneTypeOutputDTO.class)) }),
            @ApiResponse(responseCode = "404", description = NO_CONTENT_ID,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "409", description = TELEPHONE_TYPE_NAME_EXISTS,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", description = "Telephone type can't updated by a system error",
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageSimple> update(TelephoneTypeUpdateDTO entityDto) {
        try {
            return answerSimple(service.getUpdate(entityDto));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Delete a registry of telephone type", description = "Delete a registry of telephone type by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = SUCCESSFULLY_DELETED_TELEPHONE_TYPE,
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
            @ApiResponse(responseCode = "500", description = "Telephone type can't deleted by a system error",
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