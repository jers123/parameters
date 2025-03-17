package org.jers.parameters.service;

import org.jers.parameters.model.dto.BaseEntityInputDTO;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;
import org.jers.parameters.utils.response.ReplyMessageList;
import org.jers.parameters.utils.response.ReplyMessageSimple;
import org.springframework.transaction.annotation.Transactional;

public interface IBaseService<BC extends BaseEntityInputDTO, BU extends BaseEntityInputDTO, BO extends BaseEntityOutputDTO> {
    @Transactional()
    ReplyMessageSimple<BO> getCreate(BC entityDto);

    @Transactional(readOnly = true)
    ReplyMessageList<BO> getFindAll();

    @Transactional(readOnly = true)
    ReplyMessageList<BO> getFindAllByStatus(Boolean status);

    @Transactional(readOnly = true)
    ReplyMessageSimple<BO> getFindById(Integer id);

    @Transactional()
    ReplyMessageSimple<BO> getUpdate(BU entityDto);

    @Transactional()
    ReplyMessageSimple<BO> getDelete(Integer id);

    String getUri(String method);

    String getUri(String method, Integer id);
}