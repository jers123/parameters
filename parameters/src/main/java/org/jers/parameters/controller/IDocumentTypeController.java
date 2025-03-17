package org.jers.parameters.controller;

import org.jers.parameters.model.dto.documenttype.DocumentTypeCreateDTO;
import org.jers.parameters.model.dto.documenttype.DocumentTypeUpdateDTO;
import org.jers.parameters.utils.response.ReplyMessageList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.jers.parameters.utils.SystemConstants.GET_ALL_ACRONYM_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_STATUS_ACRONYM_PATH;
import static org.jers.parameters.utils.SystemConstants.STATUS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface IDocumentTypeController extends IBaseController<DocumentTypeCreateDTO, DocumentTypeUpdateDTO> {
    @GetMapping(value = GET_ALL_ACRONYM_PATH, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ReplyMessageList> getAllAcronym();

    @GetMapping(value = GET_STATUS_ACRONYM_PATH + "{" + STATUS + "}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ReplyMessageList> getByStatusAcronym(@PathVariable(STATUS) Boolean status);

}