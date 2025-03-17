package org.jers.parameters.service;

import org.jers.parameters.model.dto.civilstatus.CivilStatusCreateDTO;
import org.jers.parameters.model.dto.civilstatus.CivilStatusOutputDTO;
import org.jers.parameters.model.dto.civilstatus.CivilStatusUpdateDTO;
import org.jers.parameters.model.entity.CivilStatus;
import org.jers.parameters.model.repository.ICivilStatusRepository;
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

import static org.jers.parameters.utils.Constants.CIVIL_STATUS_NAME_EXISTS;
import static org.jers.parameters.utils.Constants.NO_CONTENT;
import static org.jers.parameters.utils.Constants.NO_CONTENT_ID;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_CREATED_CIVIL_STATUS;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_DELETED_CIVIL_STATUS;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_UPDATED_CIVIL_STATUS;
import static org.jers.parameters.utils.Constants.YES_CONTENT;
import static org.jers.parameters.utils.SystemConstants.CIVIL_STATUS_PATH;
import static org.jers.parameters.utils.SystemConstants.CREATE_PATH;
import static org.jers.parameters.utils.SystemConstants.DELETE_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_ALL_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_ID_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_STATUS_PATH;
import static org.jers.parameters.utils.SystemConstants.PARAMETERS_PATH;
import static org.jers.parameters.utils.SystemConstants.UPDATE_PATH;

@Service
@Validated
public class CivilStatusService implements IBaseService<CivilStatusCreateDTO, CivilStatusUpdateDTO, CivilStatusOutputDTO> {
    @Autowired
    private ICivilStatusRepository repository;

    @Autowired
    private ReplyMessageSimple<CivilStatusOutputDTO> replyMessageSimple;

    @Autowired
    private ReplyMessageList<CivilStatusOutputDTO> replyMessageList;

    @Autowired
    private IMapper<CivilStatusCreateDTO, CivilStatusUpdateDTO, CivilStatusOutputDTO, CivilStatus> mapper;

    @Override
    public ReplyMessageSimple<CivilStatusOutputDTO> getCreate(CivilStatusCreateDTO entityDto) {
        replyMessageSimple.setUri(getUri(CREATE_PATH));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            String name = repository.searchByName(0, entityDto.getCivilStatusName());
            if (name == null) {
                CivilStatus entity = mapper.create(entityDto);
                CivilStatusOutputDTO entityOutput = mapper.read(repository.save(entity));
                replyMessageSimple.setHttpStatus(HttpStatus.CREATED);
                replyMessageSimple.setError(false);
                messages.add(SUCCESSFULLY_CREATED_CIVIL_STATUS);
                replyMessageSimple.setResponse(entityOutput);
            } else {
                replyMessageSimple.setHttpStatus(HttpStatus.BAD_REQUEST);
                messages.add(CIVIL_STATUS_NAME_EXISTS);
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
    public ReplyMessageList<CivilStatusOutputDTO> getFindAll() {
        replyMessageList.setUri(getUri(GET_ALL_PATH));
        replyMessageList.setError(true);
        replyMessageList.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            List<CivilStatus> entities = repository.searchAll();
            if (!entities.isEmpty()) {
                List<CivilStatusOutputDTO> entitiesDto = mapper.readList(entities);
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
    public ReplyMessageList<CivilStatusOutputDTO> getFindAllByStatus(Boolean status) {
        replyMessageList.setUri(getUri(GET_STATUS_PATH));
        replyMessageList.setError(true);
        replyMessageList.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            List<CivilStatus> entities = repository.searchAllStatus(status);
            if (!entities.isEmpty()) {
                List<CivilStatusOutputDTO> entitiesDto = mapper.readList(entities);
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
    public ReplyMessageSimple<CivilStatusOutputDTO> getFindById(Integer id) {
        replyMessageSimple.setUri(getUri(GET_ID_PATH, id));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            CivilStatus entity = repository.findById(id).orElse(null);
            if (entity != null) {
                CivilStatusOutputDTO entityOutput = mapper.read(entity);
                replyMessageSimple.setHttpStatus(HttpStatus.OK);
                messages.add(YES_CONTENT);
                replyMessageSimple.setResponse(entityOutput);
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
    public ReplyMessageSimple<CivilStatusOutputDTO> getUpdate(CivilStatusUpdateDTO entityDto) {
            replyMessageSimple.setUri(getUri(UPDATE_PATH));
            replyMessageSimple.setError(true);
            replyMessageSimple.setResponse(null);
            List<String> messages = new ArrayList<>();
            try {
                CivilStatus entity = repository.findById(entityDto.getIdCivilStatus()).orElse(null);
                if (entity != null) {
                    String name = repository.searchByName(entityDto.getIdCivilStatus(), entityDto.getCivilStatusName());
                    if (name == null) {
                        CivilStatusOutputDTO entityOutput = mapper.read(repository.save(entity));
                        replyMessageSimple.setHttpStatus(HttpStatus.CREATED);
                        replyMessageSimple.setError(false);
                        messages.add(SUCCESSFULLY_UPDATED_CIVIL_STATUS);
                        replyMessageSimple.setResponse(entityOutput);
                    } else {
                        replyMessageSimple.setHttpStatus(HttpStatus.BAD_REQUEST);
                        messages.add(CIVIL_STATUS_NAME_EXISTS);
                    }
                } else {
                    replyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);
                    messages.add(NO_CONTENT_ID + entityDto.getIdCivilStatus());
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
    public ReplyMessageSimple<CivilStatusOutputDTO> getDelete(Integer id) {
        replyMessageSimple.setUri(getUri(DELETE_PATH, id));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            CivilStatus entity = repository.findById(id).orElse(null);
            if (entity != null) {
                repository.delete(entity);
                replyMessageSimple.setHttpStatus(HttpStatus.OK);
                replyMessageSimple.setError(false);
                messages.add(SUCCESSFULLY_DELETED_CIVIL_STATUS);
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
        return PARAMETERS_PATH + CIVIL_STATUS_PATH + method;
    }

    @Override
    public String getUri(String method, Integer id) {
        return PARAMETERS_PATH + CIVIL_STATUS_PATH + method + id;
    }
}