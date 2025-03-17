package org.jers.parameters.service;

import org.jers.parameters.model.dto.sector.SectorCreateDTO;
import org.jers.parameters.model.dto.sector.SectorOutputDTO;
import org.jers.parameters.model.dto.sector.SectorUpdateDTO;
import org.jers.parameters.model.entity.Sector;
import org.jers.parameters.model.repository.ISectorRepository;
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
import static org.jers.parameters.utils.Constants.SECTOR_NAME_EXISTS;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_CREATED_SECTOR;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_DELETED_SECTOR;
import static org.jers.parameters.utils.Constants.SUCCESSFULLY_UPDATED_SECTOR;
import static org.jers.parameters.utils.Constants.YES_CONTENT;
import static org.jers.parameters.utils.SystemConstants.CREATE_PATH;
import static org.jers.parameters.utils.SystemConstants.DELETE_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_ALL_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_ID_PATH;
import static org.jers.parameters.utils.SystemConstants.GET_STATUS_PATH;
import static org.jers.parameters.utils.SystemConstants.PARAMETERS_PATH;
import static org.jers.parameters.utils.SystemConstants.SECTOR_PATH;
import static org.jers.parameters.utils.SystemConstants.UPDATE_PATH;

@Service
@Validated
public class SectorService implements IBaseService<SectorCreateDTO, SectorUpdateDTO, SectorOutputDTO> {

    @Autowired
    private ISectorRepository repository;

    @Autowired
    private ReplyMessageSimple<SectorOutputDTO> replyMessageSimple;

    @Autowired
    private ReplyMessageList<SectorOutputDTO> replyMessageList;

    @Autowired
    private IMapper<SectorCreateDTO, SectorUpdateDTO, SectorOutputDTO, Sector> mapper;

    @Override
    public ReplyMessageSimple<SectorOutputDTO> getCreate(SectorCreateDTO entityDto) {
        replyMessageSimple.setUri(getUri(CREATE_PATH));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            String name = repository.searchByName(0, entityDto.getSectorName());
            if (name == null) {
                Sector entity = mapper.create(entityDto);
                SectorOutputDTO entityOutput = mapper.read(repository.save(entity));
                replyMessageSimple.setHttpStatus(HttpStatus.CREATED);
                replyMessageSimple.setError(false);
                messages.add(SUCCESSFULLY_CREATED_SECTOR);
                replyMessageSimple.setResponse(entityOutput);
            } else {
                replyMessageSimple.setHttpStatus(HttpStatus.BAD_REQUEST);
                messages.add(SECTOR_NAME_EXISTS);
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
    public ReplyMessageList<SectorOutputDTO> getFindAll() {
        replyMessageList.setUri(getUri(GET_ALL_PATH));
        replyMessageList.setError(true);
        replyMessageList.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            List<Sector> entities = repository.searchAll();
            if (!entities.isEmpty()) {
                List<SectorOutputDTO> entitiesDto = mapper.readList(entities);
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
    public ReplyMessageList<SectorOutputDTO> getFindAllByStatus(Boolean status) {
        replyMessageList.setUri(getUri(GET_STATUS_PATH));
        replyMessageList.setError(true);
        replyMessageList.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            List<Sector> entities = repository.searchAllStatus(status);
            if (!entities.isEmpty()) {
                List<SectorOutputDTO> entitiesDto = mapper.readList(entities);
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
    public ReplyMessageSimple<SectorOutputDTO> getFindById(Integer id) {
        replyMessageSimple.setUri(getUri(GET_ID_PATH, id));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            Sector entity = repository.findById(id).orElse(null);
            if (entity != null) {
                SectorOutputDTO entityDto = mapper.read(entity);
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
    public ReplyMessageSimple<SectorOutputDTO> getUpdate(SectorUpdateDTO entityDto) {
        replyMessageSimple.setUri(getUri(UPDATE_PATH));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            Sector entity = repository.findById(entityDto.getIdSector()).orElse(null);
            if (entity != null) {
                String name = repository.searchByName(entityDto.getIdSector(), entityDto.getSectorName());
                if (name == null) {
                    entity = mapper.update(entityDto, entity);
                    SectorOutputDTO entityOutput = mapper.read(repository.save(entity));
                    replyMessageSimple.setHttpStatus(HttpStatus.CREATED);
                    replyMessageSimple.setError(false);
                    messages.add(SUCCESSFULLY_UPDATED_SECTOR);
                    replyMessageSimple.setResponse(entityOutput);
                } else {
                    replyMessageSimple.setHttpStatus(HttpStatus.BAD_REQUEST);
                    messages.add(SECTOR_NAME_EXISTS);
                }
            } else {
                replyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);
                messages.add(NO_CONTENT_ID + entityDto.getIdSector());
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
    public ReplyMessageSimple<SectorOutputDTO> getDelete(Integer id) {
        replyMessageSimple.setUri(getUri(DELETE_PATH, id));
        replyMessageSimple.setError(true);
        replyMessageSimple.setResponse(null);
        List<String> messages = new ArrayList<>();
        try {
            Sector entity = repository.findById(id).orElse(null);
            if (entity != null) {
                repository.delete(entity);
                replyMessageSimple.setHttpStatus(HttpStatus.OK);
                replyMessageSimple.setError(false);
                messages.add(SUCCESSFULLY_DELETED_SECTOR);
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
        return PARAMETERS_PATH + SECTOR_PATH + method;
    }

    @Override
    public String getUri(String method, Integer id) {
        return PARAMETERS_PATH + SECTOR_PATH + method + id;
    }
}