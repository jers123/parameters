package org.castor.employee.model.repository;

import java.util.List;
import org.castor.employee.model.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import static org.castor.employee.utils.SystemConstants.CARGO_ALL_QUERY;
import static org.castor.employee.utils.SystemConstants.ID;
import static org.castor.employee.utils.SystemConstants.NOMBRE;
import static org.castor.employee.utils.SystemConstants.NOMBRE_CARGO_QUERY;;

@Repository
public interface ICargoRepository extends JpaRepository<Cargo, Integer> {
	@Query(value = CARGO_ALL_QUERY)
	List<Cargo> searchAll();

	@Query(value = NOMBRE_CARGO_QUERY)
	String searchByNombre(@Param(ID) Integer id, @Param(NOMBRE) String nombre);
}