package org.jers.parameters.model.repository;

import org.jers.parameters.model.entity.TelephoneType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jers.parameters.utils.SystemConstants.ID;
import static org.jers.parameters.utils.SystemConstants.NAME;
import static org.jers.parameters.utils.SystemConstants.STATUS;
import static org.jers.parameters.utils.SystemConstants.TELEPHONE_TYPE_ALL_QUERY;
import static org.jers.parameters.utils.SystemConstants.TELEPHONE_TYPE_ALL_STATUS_QUERY;
import static org.jers.parameters.utils.SystemConstants.TELEPHONE_TYPE_NAME_QUERY;

@Repository
public interface ITelephoneTypeRepository extends IBaseRepository<TelephoneType> {
    @Query(value = TELEPHONE_TYPE_ALL_QUERY)
    List<TelephoneType> searchAll();

    @Query(value = TELEPHONE_TYPE_ALL_STATUS_QUERY)
    List<TelephoneType> searchAllStatus(@Param(STATUS) Boolean status);

    @Query(value = TELEPHONE_TYPE_NAME_QUERY)
    String searchByName(@Param(ID) Integer id, @Param(NAME) String name);
}