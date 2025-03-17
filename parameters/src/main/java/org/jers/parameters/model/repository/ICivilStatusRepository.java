package org.jers.parameters.model.repository;

import org.jers.parameters.model.entity.CivilStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jers.parameters.utils.SystemConstants.CIVIL_STATUS_ALL_QUERY;
import static org.jers.parameters.utils.SystemConstants.CIVIL_STATUS_ALL_STATUS_QUERY;
import static org.jers.parameters.utils.SystemConstants.CIVIL_STATUS_NAME_QUERY;
import static org.jers.parameters.utils.SystemConstants.ID;
import static org.jers.parameters.utils.SystemConstants.NAME;
import static org.jers.parameters.utils.SystemConstants.STATUS;

@Repository
public interface ICivilStatusRepository extends IBaseRepository<CivilStatus> {
    @Query(value = CIVIL_STATUS_ALL_QUERY)
    List<CivilStatus> searchAll();

    @Query(value = CIVIL_STATUS_ALL_STATUS_QUERY)
    List<CivilStatus> searchAllStatus(@Param(STATUS) Boolean status);

    @Query(value = CIVIL_STATUS_NAME_QUERY)
    String searchByName(@Param(ID) Integer id, @Param(NAME) String name);
}
