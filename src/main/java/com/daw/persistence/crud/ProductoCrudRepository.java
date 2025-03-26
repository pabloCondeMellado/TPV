package com.daw.persistence.crud;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Producto;

public interface ProductoCrudRepository extends ListCrudRepository<Producto, Integer> {

}
