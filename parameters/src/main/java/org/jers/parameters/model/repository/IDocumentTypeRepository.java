package org.jers.parameters.model.repository;

import org.jers.parameters.model.entity.DocumentType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jers.parameters.utils.SystemConstants.ACRONYM;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_ACRONYM_QUERY;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_ALL_QUERY_ACRONYM;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_ALL_QUERY_NAME;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_ALL_STATUS_ACRONYM_QUERY;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_ALL_STATUS_NAME_QUERY;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_NAME_QUERY;
import static org.jers.parameters.utils.SystemConstants.ID;
import static org.jers.parameters.utils.SystemConstants.NAME;
import static org.jers.parameters.utils.SystemConstants.STATUS;

@Repository
public interface IDocumentTypeRepository extends IBaseRepository<DocumentType> {
    @Query(value = DOCUMENT_TYPE_ALL_QUERY_NAME)
    List<DocumentType> searchAll();

    @Query(value = DOCUMENT_TYPE_ALL_QUERY_ACRONYM)
    List<DocumentType> searchAllByAcronym();

    @Query(value = DOCUMENT_TYPE_ALL_STATUS_NAME_QUERY)
    List<DocumentType> searchAllStatus(@Param(STATUS) Boolean status);

    @Query(value = DOCUMENT_TYPE_ALL_STATUS_ACRONYM_QUERY)
    List<DocumentType> searchAllStatusAcronym(@Param(STATUS) Boolean status);

    @Query(value = DOCUMENT_TYPE_NAME_QUERY)
    String searchByName(@Param(ID) Integer id, @Param(NAME) String name);

    @Query(value = DOCUMENT_TYPE_ACRONYM_QUERY)
    String searchByAcronym(@Param(ID) Integer id, @Param(ACRONYM) String acronym);
}