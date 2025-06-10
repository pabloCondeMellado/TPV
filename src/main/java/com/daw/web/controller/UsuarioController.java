package com.daw.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Rol;
import com.daw.persistence.entities.Usuario;
import com.daw.services.UsuarioService;
import com.daw.services.dto.LoginRequest;
import com.daw.services.dto.LoginResponse;
import com.daw.services.dto.UsuarioDto;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listaUsuario(){
		return ResponseEntity.ok(this.usuarioService.findAll());
	}

	@GetMapping("/{idUsuario}")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable int idUsuario){
		Optional<Usuario> usuario = this.usuarioService.findByID(idUsuario);
		if(usuario.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario.get());
	}
	
	@PostMapping
	public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
	    // Codifica la contraseña en texto plano usando el bean PasswordEncoder
	    String rawPassword = usuario.getContrasenia();
	    String hash = passwordEncoder.encode(rawPassword);
	    usuario.setContrasenia(hash);

	    return new ResponseEntity<Usuario>(usuarioService.create(usuario), HttpStatus.CREATED);
	}

	@PutMapping("/{idUsuario}")
	public ResponseEntity<UsuarioDto> updateUsuario(@RequestBody UsuarioDto usuarioDto, @PathVariable int idUsuario) {
	    if (idUsuario != usuarioDto.getId()) {
	        return ResponseEntity.badRequest().build();
	    }
	    Optional<Usuario> usuarioBD = usuarioService.findByID(idUsuario);
	    if (usuarioBD.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    Usuario usuarioEntidad = usuarioBD.get();
	    usuarioEntidad.setNombre(usuarioDto.getNombre());
	    usuarioEntidad.setRol(Rol.valueOf(usuarioDto.getRol()));
	    // Solo codifica si la contraseña es nueva y no está vacía
	    if (usuarioDto.getContrasenia() != null && !usuarioDto.getContrasenia().isBlank()) {
	        String rawPassword = usuarioDto.getContrasenia();
	        String hash = passwordEncoder.encode(rawPassword);
	        usuarioDto.setContrasenia(hash);
	    } 
	    Usuario usuarioActualizado = usuarioService.update(usuarioEntidad);
	    
	    UsuarioDto respuestaDto = new UsuarioDto();
	    respuestaDto.setId(usuarioActualizado.getId());
	    respuestaDto.setNombre(usuarioActualizado.getNombre());
	    respuestaDto.setRol(usuarioActualizado.getRol().name());
	    // No devuelvas la contraseña

	    return ResponseEntity.ok(respuestaDto);
	}


	
	@DeleteMapping("/{idUsuario}")
	public ResponseEntity<Usuario> deleteUsuario(@PathVariable int idUsuario){
		if(this.usuarioService.deleteUsuario(idUsuario)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	


}
