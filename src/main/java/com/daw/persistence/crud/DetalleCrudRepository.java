package com.daw.persistence.crud;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Detalle;

public interface DetalleCrudRepository extends ListCrudRepository<Detalle, Integer>{
	Optional<Detalle> findByIdCuentaAndIdProducto(Integer idCuenta, Integer idProducto);
	List<Detalle> findByIdCuenta(int idCuenta);
}
