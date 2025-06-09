package com.daw.persistence.crud;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Producto;
import com.daw.persistence.entities.Sector;

public interface ProductoCrudRepository extends ListCrudRepository<Producto, Integer> {
	List<Producto> findBySector(Sector sector);
	List<Producto> findBySectorNot(Sector sector);
}
