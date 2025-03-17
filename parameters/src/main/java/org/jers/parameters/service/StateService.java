package org.jers.parameters.service;

import org.jers.parameters.model.dto.state.StateCreateDTO;
import org.jers.parameters.model.dto.state.StateOutputDTO;
import org.jers.parameters.model.dto.state.StateUpdateDTO;
import org.jers.parameters.model.entity.State;
import org.jers.parameters.model.repository.IStateRepository;
import org.jers.parameters.utils.mappers.IMapper;
import org.jers.parameters.utils.response.ReplyMessageList;
import org.jers.parameters.utils.response.ReplyMessageSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class StateService implements IBaseService<StateCreateDTO, StateUpdateDTO, StateOutputDTO> {

    @Autowired
    private IStateRepository repository;

    @Autowired
    private ReplyMessageSimple<StateOutputDTO> replyMessageSimple;

    @Autowired
    private ReplyMessageList<StateOutputDTO> replyMessageList;

    @Autowired
    private IMapper<StateCreateDTO, StateUpdateDTO, StateOutputDTO, State> mapper;

    @Override
    public ReplyMessageSimple<StateOutputDTO> getCreate(StateCreateDTO entityDto) {
        return null;
    }

    @Override
    public ReplyMessageList<StateOutputDTO> getFindAll() {
        return null;
    }

    @Override
    public ReplyMessageList<StateOutputDTO> getFindAllByStatus(Boolean status) {
        return null;
    }

    @Override
    public ReplyMessageSimple<StateOutputDTO> getFindById(Integer id) {
        return null;
    }

    @Override
    public ReplyMessageSimple<StateOutputDTO> getUpdate(StateUpdateDTO entityDto) {
        return null;
    }

    @Override
    public ReplyMessageSimple<StateOutputDTO> getDelete(Integer id) {
        return null;
    }

    @Override
    public String getUri(String method) {
        return "";
    }

    @Override
    public String getUri(String method, Integer id) {
        return "";
    }
}