package com.daw.web.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.daw.persistence.entities.Informe;
import com.daw.services.CuentaService;
import com.daw.services.InformeService;
import com.daw.services.dto.CuentaDto;

@RestController
@RequestMapping("/informe")
public class InformeController {
	@Autowired
	private CuentaService cuentaService;
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
	
	@GetMapping("/generar/pdf")
	public ResponseEntity<byte[]> descargarInformePdf() {
	    try {
	        // Obtén las cuentas cerradas de hoy
	        List<CuentaDto> cuentasCerradas = cuentaService.getCuentasCerradasHoyRoot();

	        // Toma la fecha del primer registro, o la fecha actual si la lista está vacía
	        LocalDateTime fechaInformeDateTime = !cuentasCerradas.isEmpty() && cuentasCerradas.get(0).getFecha() != null
	            ? cuentasCerradas.get(0).getFecha()
	            : LocalDateTime.now();
	        LocalDate fechaInforme = fechaInformeDateTime.toLocalDate();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        String fechaFormateada = fechaInforme.format(formatter);
	        // Pasa la fecha al servicio para que también la use dentro del PDF
	        byte[] pdf = informeService.generarInforme(fechaInforme);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.setContentDispositionFormData("attachment", "informe_" + fechaFormateada + ".pdf");
	        return ResponseEntity.ok().headers(headers).body(pdf);
	    } catch(Exception e) {
	        return ResponseEntity.internalServerError().build();
	    }
	}

}
