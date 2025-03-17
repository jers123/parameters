package org.jers.parameters.service;

import org.jers.parameters.model.dto.gender.GenderCreateDTO;
import org.jers.parameters.model.dto.gender.GenderOutputDTO;
import org.jers.parameters.model.dto.gender.GenderUpdateDTO;
import org.jers.parameters.model.entity.Gender;
import org.jers.parameters.model.repository.IGenderRepository;
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
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_CREATED_GENDER;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_DELETED_GENDER;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_UPDATED_GENDER;
import static org.jers.parameters.utils.Constants.GENDER_NAME_EXISTS;
import static org.jers.parameters.utils.Constants.YES_CONTENT;
import static org.jers.parameters.utils.SystemConstants.CREATE_PATH;
import static org.jers.parameters.utils.SystemConstants.DELETE_PATH;
import static org.jers.parameters.utils.SystemConstants.GENDER_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_ALL_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_ID_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_STATUS_PATH;
import static org.jers.parameters.utils.SystemConstants.PARAMETERS_PATH;
import static org.jers.parameters.utils.SystemConstants.UPDATE_PATH;

@Service
@Validated
public class GenderService implements IBaseService<GenderCreateDTO, GenderUpdateDTO, GenderOutputDTO> {

    @Autowired
    private IGenderRepository repository;

    @Autowired
    private ReplyMessageSimple<GenderOutputDTO> replyMessageSimple;

    @Autowired
    private ReplyMessageList<GenderOutputDTO> replyMessageList;

    @Autowired
    private IMapper<GenderCreateDTO, GenderUpdateDTO, GenderOutputDTO, Gender> mapper;

    @Override
    public ReplyMessageSimple<GenderOutputDTO> getCreate(GenderCreateDTO entityDto) {
        replyMessageSimple.setUri(getUri(CREATE_PATH));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            String name = repository.searchByName(0, entityDto.getGenderName());
            if (name == null) {
                Gender entity = mapper.create(entityDto);
                GenderOutputDTO entityOutput = mapper.read(repository.save(entity));
                replyMessageSimple.setHttpStatus(HttpStatus.CREATED);
                replyMessageSimple.setError(false);
                messages.add(SUCCESSFULLY_CREATED_GENDER);
                replyMessageSimple.setResponse(entityOutput);
            } else {
                replyMessageSimple.setHttpStatus(HttpStatus.CONFLICT);
                messages.add(GENDER_NAME_EXISTS);
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
    public ReplyMessageList<GenderOutputDTO> getFindAll() {
        replyMessageList.setUri(getUri(GET_ALL_PATH));
        replyMessageList.setError(true);
        replyMessageList.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            List<Gender> entities = repository.searchAll();
            if (!entities.isEmpty()) {
                List<GenderOutputDTO> entitiesDto = mapper.readList(entities);
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
    public ReplyMessageList<GenderOutputDTO> getFindAllByStatus(Boolean status) {
        replyMessageList.setUri(getUri(GET_STATUS_PATH));
        replyMessageList.setError(true);
        replyMessageList.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            List<Gender> entities = repository.searchAllStatus(status);
            if (!entities.isEmpty()) {
                List<GenderOutputDTO> entitiesDto = mapper.readList(entities);
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
    public ReplyMessageSimple<GenderOutputDTO> getFindById(Integer id) {
        replyMessageSimple.setUri(getUri(GET_ID_PATH, id));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            Gender entity = repository.findById(id).orElse(null);
            if (entity != null) {
                GenderOutputDTO entityDto = mapper.read(entity);
                replyMessageSimple.setHttpStatus(HttpStatus.OK);
                replyMessageSimple.setError(false);
                messages.add(YES_CONTENT);
                replyMessageSimple.setResponse(entityDto);
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
    public ReplyMessageSimple<GenderOutputDTO> getUpdate(GenderUpdateDTO entityDto) {
        replyMessageSimple.setUri(getUri(UPDATE_PATH));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            Gender entity = repository.findById(entityDto.getIdGender()).orElse(null);
            if (entity != null) {
                String name = repository.searchByName(entityDto.getIdGender(), entityDto.getGenderName());
                if (name == null) {
                    entity = mapper.update(entityDto, entity);
                    GenderOutputDTO entityOutput = mapper.read(repository.save(entity));
                    replyMessageSimple.setHttpStatus(HttpStatus.OK);
                    replyMessageSimple.setError(false);
                    messages.add(SUCCESSFULLY_UPDATED_GENDER);
                    replyMessageSimple.setResponse(entityOutput
                    );
                } else {
                    replyMessageSimple.setHttpStatus(HttpStatus.CONFLICT);
                    messages.add(GENDER_NAME_EXISTS);
                }
            } else {
                replyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);
                messages.add(NO_CONTENT_ID + entityDto.getIdGender());
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
    public ReplyMessageSimple<GenderOutputDTO> getDelete(Integer id) {
        replyMessageSimple.setUri(getUri(DELETE_PATH, id));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            Gender entity = repository.findById(id).orElse(null);
            if (entity != null) {
                repository.delete(entity);
                replyMessageSimple.setHttpStatus(HttpStatus.OK);
                replyMessageSimple.setError(false);
                messages.add(SUCCESSFULLY_DELETED_GENDER);
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
        return PARAMETERS_PATH + GENDER_PATH + method;
    }

    @Override
    public String getUri(String method, Integer id) {
        return PARAMETERS_PATH + GENDER_PATH + method + id;
    }
}