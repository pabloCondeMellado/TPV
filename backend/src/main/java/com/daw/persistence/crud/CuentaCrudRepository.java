package com.daw.persistence.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Cuenta;
import com.daw.persistence.entities.Estado;
import com.daw.persistence.entities.Rol;

public interface CuentaCrudRepository extends ListCrudRepository<Cuenta, Integer>{
	List<Cuenta> findByEstadoAndFechaBetweenAndUsuarioRol(Estado estado,LocalDateTime inicio,LocalDateTime fin,Rol rol);
	List<Cuenta> findByEstadoAndFechaBetween(Estado estado,LocalDateTime inicio,LocalDateTime fin);
}
