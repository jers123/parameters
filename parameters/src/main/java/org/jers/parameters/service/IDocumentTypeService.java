package org.jers.parameters.service;

import org.jers.parameters.model.dto.documenttype.DocumentTypeCreateDTO;
import org.jers.parameters.model.dto.documenttype.DocumentTypeOutputDTO;
import org.jers.parameters.model.dto.documenttype.DocumentTypeUpdateDTO;
import org.jers.parameters.utils.response.ReplyMessageList;
import org.springframework.transaction.annotation.Transactional;

public interface IDocumentTypeService<BO extends DocumentTypeOutputDTO> extends IBaseService<DocumentTypeCreateDTO, DocumentTypeUpdateDTO, BO> {
    @Transactional(readOnly = true)
    ReplyMessageList<BO> getFindAllByAcronym();

    @Transactional(readOnly = true)
    ReplyMessageList<BO> getFindAllByStatusAcronym(Boolean status);
}