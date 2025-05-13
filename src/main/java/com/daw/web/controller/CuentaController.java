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

import com.daw.persistence.entities.Cuenta;
import com.daw.services.CuentaService;
import com.daw.services.ProductoService;
import com.daw.services.dto.CuentaDto;
import com.daw.services.dto.DetalleInputDto;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {
	@Autowired
	private ProductoService productoService;
	@Autowired
	private CuentaService cuentaService;
	
	@GetMapping
	public ResponseEntity<List<CuentaDto>> listaCuenta(){
		return ResponseEntity.ok(this.cuentaService.findAll());
	}
	
	@GetMapping("/{idCuenta}")
	public ResponseEntity<CuentaDto> getCuentaById(@PathVariable int idCuenta){
		if(this.cuentaService.existsCuenta(idCuenta)) {
			return ResponseEntity.ok(this.cuentaService.findById(idCuenta));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<CuentaDto> createCuenta(@RequestBody Cuenta cuenta){
		if(!this.cuentaService.existsCuenta(cuenta.getIdUsuario())) {
			return ResponseEntity.notFound().build();
		}
		return new ResponseEntity<CuentaDto>(this.cuentaService.create(cuenta), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idCuenta}")
	public ResponseEntity<Cuenta> updateCuenta(@RequestBody Cuenta cuenta, @PathVariable int idCuenta){
		if(idCuenta != cuenta.getId()) {
			return ResponseEntity.badRequest().build();
		}else if(!this.cuentaService.existsCuenta(idCuenta)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.cuentaService.update(cuenta));
	}
	
	@DeleteMapping("/{idCuenta}")
	public ResponseEntity<Cuenta> deleteCuenta(@PathVariable int idCuenta){
		if(this.cuentaService.deleteCuenta(idCuenta)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/{idCuenta}/producto")
	public ResponseEntity<CuentaDto> addProdcutoToCuenta(@PathVariable int idCuenta, @RequestBody DetalleInputDto detalleInputDto){
		if(!this.cuentaService.existsCuenta(idCuenta)) {
			return ResponseEntity.badRequest().build();
		}
		if(!this.productoService.existsProdcuto(detalleInputDto.getIdProducto())) {
			return ResponseEntity.badRequest().build();
		}
		if(detalleInputDto.getCantidad()<= 0) {
			return ResponseEntity.badRequest().build();
		}

		detalleInputDto.setIdCuenta(idCuenta);
		CuentaDto cuentaActualizada = cuentaService.addProducto(detalleInputDto);
		
		return ResponseEntity.ok(cuentaActualizada);
	}
	
	@PutMapping("/{idCuenta}/cerrar")
	public ResponseEntity<CuentaDto> cerrarCuenta(@PathVariable int idCuenta, @RequestBody String metodoPago){
		if(!cuentaService.existsCuenta(idCuenta)) {
			return ResponseEntity.badRequest().build();
		}
		CuentaDto cuenta = cuentaService.cerrarCuenta(idCuenta, metodoPago);
		return ResponseEntity.ok(cuenta);
	}
	
	@GetMapping("/abiertas/hoy")
	public ResponseEntity<List<CuentaDto>> getCuentasAbiertasHoy(){
		List<CuentaDto> cuentas = cuentaService.getCuentasAbiertasHoy();
		return ResponseEntity.ok(cuentas);
	}
}
