package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.daw.persistence.crud.UsuarioCrudRepository;
import com.daw.persistence.entities.Usuario;

@Service
public class UsuarioService implements UserDetailsService {
	@Autowired
	private UsuarioCrudRepository usuarioCrudRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
	    System.out.println("Buscando usuario en BD: " + nombre);
	    return usuarioCrudRepository.findByNombre(nombre)
	        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
	}

	
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
	public Optional<Usuario> findByNombre(String nombre) {
	    return usuarioCrudRepository.findByNombre(nombre);
	}

	
}
