package com.daw.persistence.crud;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Cuenta;

public interface CuentaCrudRepository extends ListCrudRepository<Cuenta, Integer>{

}
