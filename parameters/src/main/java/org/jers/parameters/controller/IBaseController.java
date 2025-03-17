package org.jers.parameters.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.jers.parameters.model.dto.BaseEntityInputDTO;
import org.jers.parameters.utils.response.ReplyMessageList;
import org.jers.parameters.utils.response.ReplyMessageSimple;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.jers.parameters.utils.Constants.ID_VALUE_MINIMUM;
import static org.jers.parameters.utils.SystemConstants.CREATE_PATH;
import static org.jers.parameters.utils.SystemConstants.DELETE_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_ALL_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_ID_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_STATUS_PATH;
import static org.jers.parameters.utils.SystemConstants.ID;
import static org.jers.parameters.utils.SystemConstants.STATUS;
import static org.jers.parameters.utils.SystemConstants.UPDATE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface IBaseController<BC extends BaseEntityInputDTO, BU extends BaseEntityInputDTO> {
    @PostMapping(value = CREATE_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ReplyMessageSimple> create(@Valid @RequestBody BC entityDto);

    @GetMapping(GET_ALL_PATH)
    ResponseEntity<ReplyMessageList> getAll();

    @GetMapping(value = GET_ID_PATH + "{" + ID + "}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ReplyMessageSimple> getById(@PathVariable(ID) @Min(value = 1, message = ID_VALUE_MINIMUM) Integer id);

    @GetMapping(value = GET_STATUS_PATH + "{" + STATUS + "}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ReplyMessageList> getByStatus(@PathVariable(STATUS) Boolean status);

    @PutMapping(value = UPDATE_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ReplyMessageSimple> update(@Valid @RequestBody BU entityDto);

    @DeleteMapping(value = DELETE_PATH + "{" + ID + "}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ReplyMessageSimple> delete(@PathVariable(ID) Integer id);
}