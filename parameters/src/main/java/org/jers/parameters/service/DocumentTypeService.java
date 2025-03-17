package org.jers.parameters.service;

import org.jers.parameters.model.dto.documenttype.DocumentTypeCreateDTO;
import org.jers.parameters.model.dto.documenttype.DocumentTypeOutputDTO;
import org.jers.parameters.model.dto.documenttype.DocumentTypeUpdateDTO;
import org.jers.parameters.model.entity.DocumentType;
import org.jers.parameters.model.repository.IDocumentTypeRepository;
import org.jers.parameters.utils.mappers.IMapper;
import org.jers.parameters.utils.response.ReplyMessageList;
import org.jers.parameters.utils.response.ReplyMessageSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.jers.parameters.utils.Constants.DOCUMENT_TYPE_ACRONYM_EXISTS;
import static org.jers.parameters.utils.Constants.DOCUMENT_TYPE_NAME_EXISTS;
import static org.jers.parameters.utils.Constants.NO_CONTENT;
import static org.jers.parameters.utils.Constants.NO_CONTENT_ID;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_CREATED_DOCUMENT_TYPE;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_DELETED_DOCUMENT_TYPE;
import static org.jers.parameters.utils.Constants.YES_CONTENT;
import static org.jers.parameters.utils.SystemConstants.CREATE_PATH;
import static org.jers.parameters.utils.SystemConstants.DELETE_PATH;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_ALL_ACRONYM_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_ALL_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_ID_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_STATUS_ACRONYM_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_STATUS_PATH;
import static org.jers.parameters.utils.SystemConstants.PARAMETERS_PATH;
import static org.jers.parameters.utils.SystemConstants.UPDATE_PATH;

@Service
@Validated
public class DocumentTypeService implements IDocumentTypeService<DocumentTypeOutputDTO> {

    @Autowired
    private IDocumentTypeRepository repository;

    @Autowired
    private ReplyMessageSimple<DocumentTypeOutputDTO> replyMessageSimple;

    @Autowired
    private ReplyMessageList<DocumentTypeOutputDTO> replyMessageList;

    @Autowired
    private IMapper<DocumentTypeCreateDTO, DocumentTypeUpdateDTO, DocumentTypeOutputDTO, DocumentType> mapper;

    @Override
    public ReplyMessageSimple<DocumentTypeOutputDTO> getCreate(DocumentTypeCreateDTO entityDto) {
        replyMessageSimple.setUri(getUri(CREATE_PATH));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            String name = repository.searchByName(0, entityDto.getDocumentTypeName());
            String acronym = repository.searchByAcronym(0, entityDto.getDocumentTypeAcronym());
            if (name != null) {
                messages.add(DOCUMENT_TYPE_NAME_EXISTS);
            }
            if (acronym != null) {
                messages.add(DOCUMENT_TYPE_ACRONYM_EXISTS);
            }
            if (name == null && acronym == null) {
                DocumentType entity = mapper.create(entityDto);
                DocumentTypeOutputDTO entityOutput = mapper.read(repository.save(entity));
                replyMessageSimple.setHttpStatus(HttpStatus.CREATED);
                replyMessageSimple.setError(false);
                messages.add(SUCCESSFULLY_CREATED_DOCUMENT_TYPE);
                replyMessageSimple.setResponse(entityOutput);
            } else {
                replyMessageSimple.setHttpStatus(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            replyMessageSimple.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            messages.add(e.getMessage());
        }
        replyMessageSimple.setMessage(messages);
        replyMessageSimple.setDate(LocalDateTime.now());
        return replyMessageSimple;
    }

    @Override
    public ReplyMessageList<DocumentTypeOutputDTO> getFindAll() {
        replyMessageList.setUri(getUri(GET_ALL_PATH));
        replyMessageList.setError(true);
        replyMessageList.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            List<DocumentType> entities = repository.searchAll();
            if (!entities.isEmpty()) {
                List<DocumentTypeOutputDTO> entitiesDto = mapper.readList(entities);
                messages.add(YES_CONTENT);
                replyMessageList.setResponse(entitiesDto);
            } else {
                messages.add(NO_CONTENT);
            }
            replyMessageList.setHttpStatus(HttpStatus.OK);
            replyMessageList.setError(false);
        } catch (Exception e) {
            replyMessageList.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            messages.add(e.getMessage());
        }
        replyMessageList.setMessage(messages);
        replyMessageList.setDate(LocalDateTime.now());
        return replyMessageList;
    }

    @Override
    public ReplyMessageList<DocumentTypeOutputDTO> getFindAllByAcronym() {
        replyMessageList.setUri(getUri(GET_ALL_ACRONYM_PATH));
        replyMessageList.setError(true);
        replyMessageList.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            List<DocumentType> entities = repository.searchAllByAcronym();
            if (!entities.isEmpty()) {
                List<DocumentTypeOutputDTO> entitiesDto = mapper.readList(entities);
                messages.add(YES_CONTENT);
                replyMessageList.setResponse(entitiesDto);
            } else {
                messages.add(NO_CONTENT);
            }
            replyMessageList.setHttpStatus(HttpStatus.OK);
            replyMessageList.setError(false);
        } catch (Exception e) {
            replyMessageSimple.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            messages.add(e.getMessage());
        }
        replyMessageList.setMessage(messages);
        replyMessageList.setDate(LocalDateTime.now());
        return replyMessageList;
    }

    @Override
    public ReplyMessageList<DocumentTypeOutputDTO> getFindAllByStatus(Boolean status) {
        replyMessageList.setUri(getUri(GET_STATUS_PATH));
        replyMessageList.setError(true);
        replyMessageList.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            List<DocumentType> entities = repository.searchAllStatus(status);
            if (!entities.isEmpty()) {
                List<DocumentTypeOutputDTO> entitiesDto = mapper.readList(entities);
                messages.add(YES_CONTENT);
                replyMessageList.setResponse(entitiesDto);
            } else {
                messages.add(NO_CONTENT);
            }
            replyMessageList.setHttpStatus(HttpStatus.OK);
            replyMessageList.setError(false);
        } catch (Exception e) {
            replyMessageList.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            messages.add(e.getMessage());
        }
        replyMessageList.setMessage(messages);
        replyMessageList.setDate(LocalDateTime.now());
        return replyMessageList;
    }

    @Override
    public ReplyMessageList<DocumentTypeOutputDTO> getFindAllByStatusAcronym(Boolean status) {
        replyMessageList.setUri(getUri(GET_STATUS_ACRONYM_PATH));
        replyMessageList.setError(true);
        replyMessageList.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            List<DocumentType> entities = repository.searchAllStatusAcronym(status);
            if (!entities.isEmpty()) {
                List<DocumentTypeOutputDTO> entitiesDto = new ArrayList<>();
                for (DocumentType entity : entities) {
                    entitiesDto.add(mapper.read(entity));
                }
                messages.add(YES_CONTENT);
                replyMessageList.setResponse(entitiesDto);
            } else {
                messages.add(NO_CONTENT);
            }
            replyMessageList.setHttpStatus(HttpStatus.OK);
            replyMessageList.setError(false);
        } catch (Exception e) {
            replyMessageList.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            messages.add(e.getMessage());
        }
        replyMessageList.setMessage(messages);
        replyMessageList.setDate(LocalDateTime.now());
        return replyMessageList;
    }

    @Override
    public ReplyMessageSimple<DocumentTypeOutputDTO> getFindById(Integer id) {
        replyMessageSimple.setUri(getUri(GET_ID_PATH, id));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            DocumentType entity = repository.findById(id).orElse(null);
            if (entity != null) {
                DocumentTypeOutputDTO entityDto = mapper.read(entity);
                replyMessageSimple.setHttpStatus(HttpStatus.OK);
                messages.add(YES_CONTENT);
                replyMessageSimple.setResponse(entityDto);
            } else {
                replyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);
                messages.add(NO_CONTENT_ID + id);
            }
            replyMessageSimple.setError(false);
        } catch (Exception e) {
            replyMessageSimple.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            messages.add(e.getMessage());
        }
        replyMessageSimple.setMessage(messages);
        replyMessageSimple.setDate(LocalDateTime.now());
        return replyMessageSimple;
    }

    @Override
    public ReplyMessageSimple<DocumentTypeOutputDTO> getUpdate(DocumentTypeUpdateDTO entityDto) {
        replyMessageSimple.setUri(getUri(UPDATE_PATH));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            DocumentType entity = repository.findById(entityDto.getIdDocumentType()).orElse(null);
            if (entity != null) {
                String name = repository.searchByName(entityDto.getIdDocumentType(), entityDto.getDocumentTypeName());
                String acronym = repository.searchByAcronym(entityDto.getIdDocumentType(), entityDto.getDocumentTypeAcronym());
                if (name != null) {
                    messages.add(DOCUMENT_TYPE_NAME_EXISTS);
                }
                if (acronym != null) {
                    messages.add(DOCUMENT_TYPE_ACRONYM_EXISTS);
                }
                if (name == null && acronym == null) {
                    entity = mapper.update(entityDto, entity);
                    DocumentTypeOutputDTO entityOutput = mapper.read(repository.save(entity));
                    replyMessageSimple.setHttpStatus(HttpStatus.CREATED);
                    replyMessageSimple.setError(false);
                    messages.add(SUCCESSFULLY_CREATED_DOCUMENT_TYPE);
                    replyMessageSimple.setResponse(entityOutput);
                } else {
                    replyMessageSimple.setHttpStatus(HttpStatus.BAD_REQUEST);
                }
            } else {
                replyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);
                messages.add(NO_CONTENT_ID + entityDto.getIdDocumentType());
            }
        } catch (Exception e) {
            replyMessageSimple.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            messages.add(e.getMessage());
        }
        replyMessageSimple.setMessage(messages);
        replyMessageSimple.setDate(LocalDateTime.now());
        return replyMessageSimple;
    }

    @Override
    public ReplyMessageSimple<DocumentTypeOutputDTO> getDelete(Integer id) {
        replyMessageSimple.setUri(getUri(DELETE_PATH, id));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            DocumentType entity = repository.findById(id).orElse(null);
            if (entity != null) {
                repository.delete(entity);
                replyMessageSimple.setHttpStatus(HttpStatus.OK);
                replyMessageSimple.setError(false);
                messages.add(SUCCESSFULLY_DELETED_DOCUMENT_TYPE);
            } else {
                replyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);
                messages.add(NO_CONTENT_ID + id);
            }
        } catch (Exception e) {
            replyMessageSimple.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            messages.add(e.getMessage());
        }
        replyMessageSimple.setMessage(messages);
        replyMessageSimple.setDate(LocalDateTime.now());
        return replyMessageSimple;
    }

    @Override
    public String getUri(String method) {
        return PARAMETERS_PATH + DOCUMENT_TYPE_PATH + method;
    }

    @Override
    public String getUri(String method, Integer id) {
        return PARAMETERS_PATH + DOCUMENT_TYPE_PATH + method + id;
    }
}