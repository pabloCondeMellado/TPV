package com.daw.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Usuario;
import com.daw.services.UsuarioService;
import com.daw.services.dto.LoginRequest;
import com.daw.services.dto.LoginResponse;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
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
	public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario){
		return new ResponseEntity<Usuario>(this.usuarioService.create(usuario), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idUsuario}")
	public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario, @PathVariable int idUsuario){
		if(idUsuario != usuario.getId()){
			return ResponseEntity.badRequest().build();
		}else if(!this.usuarioService.existsUsuario(idUsuario)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.usuarioService.update(usuario));
	}
	
	@DeleteMapping("/{idUsuario}")
	public ResponseEntity<Usuario> deleteUsuario(@PathVariable int idUsuario){
		if(this.usuarioService.deleteUsuario(idUsuario)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
		if(loginRequest.getNombre().trim().isEmpty() || loginRequest.getNombre() == null) {
			return ResponseEntity.badRequest().build();
		}
		if(loginRequest.getContrasenia().trim().isEmpty()  || loginRequest.getContrasenia() == null) {
			return ResponseEntity.badRequest().build();
		}
		Usuario usuario = usuarioService.login(loginRequest.getNombre(), loginRequest.getContrasenia());
	    if (usuario == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    LoginResponse respuesta = new LoginResponse();
	    respuesta.setId(usuario.getId());
	    respuesta.setNombre(usuario.getNombre());
	    respuesta.setRol(usuario.getRol());
	    return ResponseEntity.ok(respuesta);
		
	}
}
