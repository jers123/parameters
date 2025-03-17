package org.jers.parameters.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jers.parameters.annotation.RestApi;
import org.jers.parameters.model.dto.sector.SectorCreateDTO;
import org.jers.parameters.model.dto.sector.SectorOutputDTO;
import org.jers.parameters.model.dto.sector.SectorUpdateDTO;
import org.jers.parameters.service.IBaseService;
import org.jers.parameters.utils.response.ReplyMessageList;
import org.jers.parameters.utils.response.ReplyMessageSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.jers.parameters.utils.Constants.NO_CONTENT;
import static org.jers.parameters.utils.Constants.NO_CONTENT_ID;
import static org.jers.parameters.utils.Constants.SECTOR_NAME_EXISTS;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_CREATED_SECTOR;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_DELETED_SECTOR;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_UPDATED_SECTOR;
import static org.jers.parameters.utils.Constants.YES_CONTENT;
import static org.jers.parameters.utils.SystemConstants.ACCEPT;
import static org.jers.parameters.utils.SystemConstants.AUTHORIZATION;
import static org.jers.parameters.utils.SystemConstants.CONTENT_TYPE;
import static org.jers.parameters.utils.SystemConstants.SECTOR_PATH;
import static org.jers.parameters.utils.SystemConstants.PARAMETERS_PATH;
import static org.jers.parameters.utils.SystemConstants.answerList;
import static org.jers.parameters.utils.SystemConstants.answerSimple;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestApi
@RequestMapping(path = PARAMETERS_PATH + SECTOR_PATH)
@Tag(name = "Sector API", description = "Create, update, read and delete sectors")
public class SectorController implements IBaseController<SectorCreateDTO, SectorUpdateDTO> {

    @Autowired
    private IBaseService<SectorCreateDTO, SectorUpdateDTO, SectorOutputDTO> service;

    @Override
    @Operation(summary = "Create a new sector", description = "Create news registries of sectors")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = SUCCESSFULLY_CREATED_SECTOR,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Sector create", description = "Crete a sector schema",
                                            implementation = SectorOutputDTO.class)) }),
            @ApiResponse(responseCode = "409", description = SECTOR_NAME_EXISTS,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", description = "Sector can't created by a system error",
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageSimple> create(SectorCreateDTO entityDto) {
        try {
            return answerSimple(service.getCreate(entityDto));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Show all registries of sectors", description = "Show all registries of sectors")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = YES_CONTENT + " OR " + NO_CONTENT,
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Sector get all", description = "Get all sector schema",
                                            implementation = SectorOutputDTO.class)) }),
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
    @Operation(summary = "Show a registry of sector by id", description = "Show a registry of sector by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = YES_CONTENT + " OR " + NO_CONTENT_ID,
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Sector get all", description = "Get all sector schema",
                                            implementation = SectorOutputDTO.class)) }),
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
    public ResponseEntity<ReplyMessageSimple> getById(//@Parameter(name = "Id sector", example = "1", description = "ID of the sector to be obtained", required = true)
                                                      Integer id) {
        try {
            return answerSimple(service.getFindById(id));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Show all registries of sector by status", description = "Show all registries of sector by status")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = YES_CONTENT,
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Sector get all by status", description = "Get all Sector schema by status",
                                            implementation = SectorOutputDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Can't get response by a system error",
                    headers = {
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageList> getByStatus(//@Parameter(name = "Status sector", example = "true", description = "Status of the sector to be obtained", required = true)
                                                        Boolean status) {
        try {
            return answerList(service.getFindAllByStatus(status));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Update a registry of sector", description = "Update a registry of sector by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = SUCCESSFULLY_UPDATED_SECTOR,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(name = "Sector update", description = "Update a sector schema",
                                            implementation = SectorOutputDTO.class)) }),
            @ApiResponse(responseCode = "404", description = NO_CONTENT_ID,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "409", description = SECTOR_NAME_EXISTS,
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", description = "Sector can't updated by a system error",
                    headers = {
                            @Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),
                            @Header(name = AUTHORIZATION, required = true),
                            @Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)
                    },
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE) })
    })
    public ResponseEntity<ReplyMessageSimple> update(SectorUpdateDTO entityDto) {
        try {
            return answerSimple(service.getUpdate(entityDto));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Operation(summary = "Delete a registry of sector", description = "Delete a registry of sector by id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = SUCCESSFULLY_DELETED_SECTOR,
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