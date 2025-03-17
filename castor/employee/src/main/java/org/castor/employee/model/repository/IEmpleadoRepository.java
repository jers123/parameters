package org.castor.employee.model.repository;

import java.util.List;
import org.castor.employee.model.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import static org.castor.employee.utils.SystemConstants.CEDULA;
import static org.castor.employee.utils.SystemConstants.CEDULA_QUERY;
import static org.castor.employee.utils.SystemConstants.EMPLEADO_ALL_QUERY;
import static org.castor.employee.utils.SystemConstants.EMPLEADO_ID_CARGO_QUERY;
import static org.castor.employee.utils.SystemConstants.FOTO;
import static org.castor.employee.utils.SystemConstants.FOTO_QUERY;
import static org.castor.employee.utils.SystemConstants.ID;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Integer> {
	@Query(value = EMPLEADO_ALL_QUERY)
	List<Empleado> searchAll();

	@Query(value = CEDULA_QUERY)
	Integer searchByCedula(@Param(ID) Integer id, @Param(CEDULA) Integer cedula);
	
	@Query(value = FOTO_QUERY)
	String searchByFoto(@Param(ID) Integer id, @Param(FOTO) String foto);
	
	@Query(value = EMPLEADO_ID_CARGO_QUERY)
	List<Empleado> searchByIdCargo(@Param(ID) Integer id);
}