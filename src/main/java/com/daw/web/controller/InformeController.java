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

import com.daw.persistence.entities.Informe;
import com.daw.services.InformeService;

@RestController
@RequestMapping("/informe")
public class InformeController {
	@Autowired
	private InformeService informeService;
	
	@GetMapping
	public ResponseEntity<List<Informe>> listaInforme(){
		return ResponseEntity.ok(this.informeService.findAll());
	}
	
	@GetMapping("/{idInforme}")
	public ResponseEntity<Informe> getInformeById(@PathVariable int idInforme){
		Optional<Informe> informe = this.informeService.findById(idInforme);
		if(informe.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(informe.get());
	}
	
	@PostMapping
	public ResponseEntity<Informe> createInforme(@RequestBody Informe informe){
		return new ResponseEntity<Informe>(this.informeService.create(informe),HttpStatus.CREATED);
	}
	
	@PutMapping("/{idInforme}")
	public ResponseEntity<Informe> updateInforme(@RequestBody Informe informe, @PathVariable int idInforme){
		if(idInforme != informe.getId()) {
			return ResponseEntity.badRequest().build();
		}else if(!this.informeService.existsInforme(idInforme)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(this.informeService.update(informe));
	}
	
	@DeleteMapping("/{idInforme}")
	public ResponseEntity<Informe> deleteInforme(@PathVariable int idInforme){
		if(this.informeService.deleteInforme(idInforme)) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
