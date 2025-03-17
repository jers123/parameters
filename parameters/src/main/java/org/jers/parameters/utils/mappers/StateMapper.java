package org.jers.parameters.utils.mappers;

import org.jers.parameters.model.dto.state.StateCreateDTO;
import org.jers.parameters.model.dto.state.StateOutputDTO;
import org.jers.parameters.model.dto.state.StateUpdateDTO;
import org.jers.parameters.model.entity.State;
import org.springframework.stereotype.Component;

@Component
public class StateMapper implements IMapper<StateCreateDTO, StateUpdateDTO, StateOutputDTO, State> {
    @Override
    public State create(StateCreateDTO entityDto) {
        return null;
    }

    @Override
    public StateOutputDTO read(State entity) {
        return null;
    }

    @Override
    public State update(StateUpdateDTO entityDto, State entity) {
        return null;
    }
}
