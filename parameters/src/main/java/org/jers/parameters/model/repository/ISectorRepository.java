package org.jers.parameters.model.repository;

import org.jers.parameters.model.entity.Sector;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jers.parameters.utils.SystemConstants.ID;
import static org.jers.parameters.utils.SystemConstants.NAME;
import static org.jers.parameters.utils.SystemConstants.SECTOR_ALL_QUERY;
import static org.jers.parameters.utils.SystemConstants.SECTOR_ALL_STATUS_QUERY;
import static org.jers.parameters.utils.SystemConstants.SECTOR_NAME_QUERY;
import static org.jers.parameters.utils.SystemConstants.STATUS;

@Repository
public interface ISectorRepository extends IBaseRepository<Sector> {
    @Query(value = SECTOR_ALL_QUERY)
    List<Sector> searchAll();

    @Query(value = SECTOR_ALL_STATUS_QUERY)
    List<Sector> searchAllStatus(@Param(STATUS) Boolean status);

    @Query(value = SECTOR_NAME_QUERY)
    String searchByName(@Param(ID) Integer id, @Param(NAME) String name);
}