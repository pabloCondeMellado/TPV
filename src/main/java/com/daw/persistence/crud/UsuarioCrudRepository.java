package com.daw.persistence.crud;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Usuario;

public interface UsuarioCrudRepository extends ListCrudRepository<Usuario, Integer> {
	Optional<Usuario> findByNombre(String nombre);
}
