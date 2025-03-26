package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.UsuarioCrudRepository;
import com.daw.persistence.entities.Usuario;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioCrudRepository usuarioCrudRepository;
	
	public List<Usuario> findAll(){
		return this.usuarioCrudRepository.findAll();
	}
	
	public Optional<Usuario> findByID(int idUsuario){
		return this.usuarioCrudRepository.findById(idUsuario);
	}
	
	public boolean existsUsuario(int idUsuario) {
		return this.usuarioCrudRepository.existsById(idUsuario);
	}
	
	public Usuario create(Usuario usuario) {
		return this.usuarioCrudRepository.save(usuario);
	}
	
	public Usuario update(Usuario usuario){
		return this.usuarioCrudRepository.save(usuario);
	}
	
	public boolean deleteUsuario(int idUsuario) {
		boolean result  = false;
		if(this.usuarioCrudRepository.existsById(idUsuario)) {
			this.usuarioCrudRepository.deleteById(idUsuario);
			result = true;
		}
		
		return result;
	}
}
