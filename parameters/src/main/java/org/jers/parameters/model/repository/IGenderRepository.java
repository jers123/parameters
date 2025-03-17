package org.jers.parameters.model.repository;

import org.jers.parameters.model.entity.Gender;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jers.parameters.utils.SystemConstants.GENDER_ALL_STATUS_QUERY;
import static org.jers.parameters.utils.SystemConstants.GENDER_ALL_QUERY;
import static org.jers.parameters.utils.SystemConstants.GENDER_NAME_QUERY;
import static org.jers.parameters.utils.SystemConstants.ID;
import static org.jers.parameters.utils.SystemConstants.NAME;
import static org.jers.parameters.utils.SystemConstants.STATUS;

@Repository
public interface IGenderRepository extends IBaseRepository<Gender> {
    @Query(value = GENDER_ALL_QUERY)
    List<Gender> searchAll();

    @Query(value = GENDER_ALL_STATUS_QUERY)
    List<Gender> searchAllStatus(@Param(STATUS) Boolean status);

    @Query(value = GENDER_NAME_QUERY)
    String searchByName(@Param(ID) Integer id, @Param(NAME) String name);
}