package org.jers.parameters.utils.mappers;

import org.jers.parameters.model.dto.documenttype.DocumentTypeCreateDTO;
import org.jers.parameters.model.dto.documenttype.DocumentTypeOutputDTO;
import org.jers.parameters.model.dto.documenttype.DocumentTypeUpdateDTO;
import org.jers.parameters.model.entity.DocumentType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DocumentTypeMapper implements IMapper<DocumentTypeCreateDTO, DocumentTypeUpdateDTO, DocumentTypeOutputDTO, DocumentType> {

    @Override
    public DocumentType create(DocumentTypeCreateDTO entityDto) {
        DocumentType entity = new DocumentType();
        entity.setDocumentTypeName(entityDto.getDocumentTypeName());
        entity.setDocumentTypeAcronym(entityDto.getDocumentTypeAcronym());
        if(entityDto.getStatus() != null) {
            entity.setStatus(entityDto.getStatus());
        } else {
            entity.setStatus(true);
        }
        entity.setCreationDate(LocalDateTime.now());
        entity.setUpdateDate(entity.getCreationDate());
        return entity;
    }

    @Override
    public DocumentTypeOutputDTO read(DocumentType entity) {
        DocumentTypeOutputDTO entityDto = new DocumentTypeOutputDTO();
        entityDto.setIdDocumentType(entity.getIdDocumentType());
        entityDto.setDocumentTypeName(entity.getDocumentTypeName());
        entityDto.setDocumentTypeAcronym(entity.getDocumentTypeAcronym());
        entityDto.setStatus(entity.getStatus());
        entityDto.setCreationDate(entity.getCreationDate());
        entityDto.setUpdateDate(entity.getUpdateDate());
        return entityDto;
    }

    @Override
    public DocumentType update(DocumentTypeUpdateDTO entityDto, DocumentType entity) {
        entity.setDocumentTypeName(entityDto.getDocumentTypeName());
        entity.setDocumentTypeAcronym(entityDto.getDocumentTypeAcronym());
        if(entityDto.getStatus() != null) {
            entity.setStatus(entityDto.getStatus());
        }
        entity.setUpdateDate(LocalDateTime.now());
        return entity;
    }
}