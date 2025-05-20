package com.daw.persistence.crud;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Usuario;

public interface UsuarioCrudRepository extends ListCrudRepository<Usuario, Integer> {
	Usuario findByNombreAndContrasenia(String nombre, String contrasenia);
}
