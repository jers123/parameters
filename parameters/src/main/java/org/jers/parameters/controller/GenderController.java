package org.jers.parameters.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jers.parameters.annotation.RestApi;
import org.jers.parameters.model.dto.gender.GenderCreateDTO;
import org.jers.parameters.model.dto.gender.GenderOutputDTO;
import org.jers.parameters.model.dto.gender.GenderUpdateDTO;
import org.jers.parameters.service.IBaseService;
import org.jers.parameters.utils.response.ReplyMessageList;
import org.jers.parameters.utils.response.ReplyMessageSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.jers.parameters.utils.Constants.GENDER_NAME_EXISTS;
import static org.jers.parameters.utils.Constants.NO_CONTENT;
import static org.jers.parameters.utils.Constants.NO_CONTENT_ID;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_CREATED_GENDER;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_DELETED_GENDER;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_UPDATED_GENDER;
import static org.jers.parameters.utils.Constants.YES_CONTENT;
import static org.jers.parameters.utils.SystemConstants.ACCEPT;
import static org.jers.parameters.utils.SystemConstants.AUTHORIZATION;
import static org.jers.parameters.utils.SystemConstants.CONTENT_TYPE;
import static org.jers.parameters.utils.SystemConstants.GENDER_PATH;
import static org.jers.parameters.utils.SystemConstants.PARAMETERS_PATH;
import static org.jers.parameters.utils.SystemConstants.answerList;
import static org.jers.parameters.utils.SystemConstants.answerSimple;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestApi
@RequestMapping(path = PARAMETERS_PATH + GENDER_PATH)
@Tag(name = "Gender API", description = "Create, update, read and delete genders")
public class GenderController implements IBaseController<GenderCreateDTO, GenderUpdateDTO> {

    @Autowired
    private IBaseService<GenderCreateDTO, GenderUpdateDTO, GenderOutputDTO> service;

    @Override
    @Operation(summary = "Create a new gender", description = "Create news registries of genders")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = SUCCESSFULLY_CREATED_GENDER,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                        @Content(mediaType = APPLICATION_JSON_VALUE,
                                schema = @Schema(name = "Gender create", description = "Crete a gender schema",
                                        implementation = GenderOutputDTO.class))}),
            @ApiResponse(responseCode = "409", description = GENDER_NAME_EXISTS,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", description = "Gender can't created by a system error",
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageSimple> create(GenderCreateDTO entityDto) {
        try {
            return answerSimple(service.getCreate(entityDto));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Show all registries of genders", description = "Show all registries of genders")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = YES_CONTENT + " OR " + NO_CONTENT,
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                        @Content(mediaType = APPLICATION_JSON_VALUE,
                                schema = @Schema(name = "Gender get all", description = "Get all gender schema",
                                        implementation = GenderOutputDTO.class)) }),
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
    @Operation(summary = "Show a registry of gender by id", description = "Show a registry of gender by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = YES_CONTENT + " OR " + NO_CONTENT_ID,
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Gender get all", description = "Get all gender schema",
                                            implementation = GenderOutputDTO.class)) }),
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
    public ResponseEntity<ReplyMessageSimple> getById(//@Parameter(name = "Id gender", example = "1", description = "ID of the gender to be obtained", required = true)
                                                    Integer id) {
        try {
            return answerSimple(service.getFindById(id));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Show all registries of gender by status", description = "Show all registries of gender by status")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = YES_CONTENT,
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Gender get all by status", description = "Get all gender schema by status",
                                            implementation = GenderOutputDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Can't get response by a system error",
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageList> getByStatus(//@Parameter(name = "Status gender", example = "true", description = "Status of the gender to be obtained", required = true)
                                                            Boolean status) {
        try {
            return answerList(service.getFindAllByStatus(status));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Update a registry of gender", description = "Update a registry of gender by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = SUCCESSFULLY_UPDATED_GENDER,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Gender update", description = "Update a gender schema",
                                            implementation = GenderOutputDTO.class)) }),
            @ApiResponse(responseCode = "404", description = NO_CONTENT_ID,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "409", description = GENDER_NAME_EXISTS,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", description = "Gender can't updated by a system error",
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageSimple> update(GenderUpdateDTO entityDto) {
        try {
            return answerSimple(service.getUpdate(entityDto));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Delete a registry of gender", description = "Delete a registry of gender by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = SUCCESSFULLY_DELETED_GENDER,
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
            @ApiResponse(responseCode = "500", description = "Gender can't deleted by a system error",
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