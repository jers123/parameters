package org.jers.parameters.service;

import org.jers.parameters.model.dto.telephonetype.TelephoneTypeCreateDTO;
import org.jers.parameters.model.dto.telephonetype.TelephoneTypeOutputDTO;
import org.jers.parameters.model.dto.telephonetype.TelephoneTypeUpdateDTO;
import org.jers.parameters.model.entity.TelephoneType;
import org.jers.parameters.model.repository.ITelephoneTypeRepository;
import org.jers.parameters.utils.response.ReplyMessage;
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

import static org.jers.parameters.utils.Constants.NO_CONTENT;
import static org.jers.parameters.utils.Constants.NO_CONTENT_ID;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_CREATED_TELEPHONE_TYPE;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_DELETED_TELEPHONE_TYPE;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_UPDATED_TELEPHONE_TYPE;
import static org.jers.parameters.utils.Constants.TELEPHONE_TYPE_NAME_EXISTS;
import static org.jers.parameters.utils.Constants.YES_CONTENT;
import static org.jers.parameters.utils.SystemConstants.CREATE_PATH;
import static org.jers.parameters.utils.SystemConstants.DELETE_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_ALL_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_ID_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_STATUS_PATH;
import static org.jers.parameters.utils.SystemConstants.PARAMETERS_PATH;
import static org.jers.parameters.utils.SystemConstants.TELEPHONE_TYPE_PATH;
import static org.jers.parameters.utils.SystemConstants.UPDATE_PATH;

@Service
@Validated
public class TelephoneTypeService implements IBaseService<TelephoneTypeCreateDTO, TelephoneTypeUpdateDTO, TelephoneTypeOutputDTO> {

    @Autowired
    private ITelephoneTypeRepository repository;

    @Autowired
    private ReplyMessageSimple<TelephoneTypeOutputDTO> replyMessageSimple;

    @Autowired
    private ReplyMessageList<TelephoneTypeOutputDTO> replyMessageList;

    @Autowired
    private IMapper<TelephoneTypeCreateDTO, TelephoneTypeUpdateDTO, TelephoneTypeOutputDTO, TelephoneType> mapper;

    @Override
    public ReplyMessageSimple<TelephoneTypeOutputDTO> getCreate(TelephoneTypeCreateDTO entityDto) {
        replyMessageSimple.setUri(getUri(CREATE_PATH));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            String name = repository.searchByName(0, entityDto.getTelephoneTypeName());
            if (name == null) {
                TelephoneType entity = mapper.create(entityDto);
                TelephoneTypeOutputDTO entityOutput = mapper.read(repository.save(entity));
                replyMessageSimple.setHttpStatus(HttpStatus.CREATED);
                replyMessageSimple.setError(false);
                messages.add(SUCCESSFULLY_CREATED_TELEPHONE_TYPE);
                replyMessageSimple.setResponse(entityOutput);
            } else {
                replyMessageSimple.setHttpStatus(HttpStatus.BAD_REQUEST);
                messages.add(TELEPHONE_TYPE_NAME_EXISTS);
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
    public ReplyMessageList<TelephoneTypeOutputDTO> getFindAll() {
        replyMessageList.setUri(getUri(GET_ALL_PATH));
        replyMessageList.setError(true);
        replyMessageList.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            List<TelephoneType> entities = repository.searchAll();
            if (!entities.isEmpty()) {
                List<TelephoneTypeOutputDTO> entitiesDto = mapper.readList(entities);
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
    public ReplyMessageList<TelephoneTypeOutputDTO> getFindAllByStatus(Boolean status) {
        replyMessageList.setUri(getUri(GET_STATUS_PATH));
        replyMessageList.setError(true);
        replyMessageList.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            List<TelephoneType> entities = repository.searchAllStatus(status);
            if (!entities.isEmpty()) {
                List<TelephoneTypeOutputDTO> entitiesDto = mapper.readList(entities);
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
    public ReplyMessageSimple<TelephoneTypeOutputDTO> getFindById(Integer id) {
        replyMessageSimple.setUri(getUri(GET_ID_PATH, id));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            TelephoneType entity = repository.findById(id).orElse(null);
            if (entity != null) {
                TelephoneTypeOutputDTO entityDto = mapper.read(entity);
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
    public ReplyMessageSimple<TelephoneTypeOutputDTO> getUpdate(TelephoneTypeUpdateDTO entityDto) {
        replyMessageSimple.setUri(getUri(UPDATE_PATH));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            TelephoneType entity = repository.findById(entityDto.getIdTelephoneType()).orElse(null);
            if (entity != null) {
                String name = repository.searchByName(entityDto.getIdTelephoneType(), entityDto.getTelephoneTypeName());
                if (name == null) {
                    entity = mapper.update(entityDto, entity);
                    TelephoneTypeOutputDTO entityOutput = mapper.read(repository.save(entity));
                    replyMessageSimple.setHttpStatus(HttpStatus.CREATED);
                    replyMessageSimple.setError(false);
                    messages.add(SUCCESSFULLY_UPDATED_TELEPHONE_TYPE);
                    replyMessageSimple.setResponse(entityOutput);
                } else {
                    replyMessageSimple.setHttpStatus(HttpStatus.BAD_REQUEST);
                    messages.add(TELEPHONE_TYPE_NAME_EXISTS);
                }
            } else {
                replyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);
                messages.add(NO_CONTENT_ID + entityDto.getIdTelephoneType());
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
    public ReplyMessageSimple<TelephoneTypeOutputDTO> getDelete(Integer id) {
        replyMessageSimple.setUri(getUri(DELETE_PATH, id));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            TelephoneType entity = repository.findById(id).orElse(null);
            if (entity != null) {
                repository.delete(entity);
                replyMessageSimple.setHttpStatus(HttpStatus.OK);
                replyMessageSimple.setError(false);
                messages.add(SUCCESSFULLY_DELETED_TELEPHONE_TYPE);
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
        return PARAMETERS_PATH + TELEPHONE_TYPE_PATH + method;
    }

    @Override
    public String getUri(String method, Integer id) {
        return PARAMETERS_PATH + TELEPHONE_TYPE_PATH + method + id;
    }
}