package org.castor.employee.service;

import org.castor.employee.model.dto.BaseEntityDTO;
import org.castor.employee.utils.response.ReplyMessageList;
import org.castor.employee.utils.response.ReplyMessageSimple;
import org.springframework.transaction.annotation.Transactional;

public interface IBaseService<BC extends BaseEntityDTO, BUO extends BaseEntityDTO> {
	@Transactional()
	ReplyMessageSimple<BUO> getCreate(BC entityDto);

	@Transactional(readOnly = true)
	ReplyMessageList<BUO> getFindAll();

	@Transactional(readOnly = true)
	ReplyMessageSimple<BUO> getFindById(Integer id);

	@Transactional()
	ReplyMessageSimple<BUO> getUpdate(BUO entityDto);

	@Transactional()
	ReplyMessageSimple<BUO> getDelete(Integer id);

	String getUri(String method);

	String getUri(String method, Integer id);
}