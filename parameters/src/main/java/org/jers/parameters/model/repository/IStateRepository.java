package org.jers.parameters.model.repository;

import org.jers.parameters.model.entity.State;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static org.jers.parameters.utils.SystemConstants.GENDER_ALL_QUERY;

public interface IStateRepository  extends IBaseRepository<State> {
    //@Query(value = GENDER_ALL_QUERY)
    //List<State> searchAll();

}