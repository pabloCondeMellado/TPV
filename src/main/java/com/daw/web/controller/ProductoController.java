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

import com.daw.persistence.entities.Producto;
import com.daw.services.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {
	@Autowired
	private ProductoService productoService;
	
	@GetMapping
	public ResponseEntity<List<Producto>> listaProducto(){
		return ResponseEntity.ok(this.productoService.findAll());
	}
	
	@GetMapping("/{idProducto}")
	public ResponseEntity<Producto> getProductoById(@PathVariable int idProducto){
		Optional<Producto> producto = this.productoService.findById(idProducto);
		if(producto.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(producto.get());
	}
	
	@PostMapping
	public ResponseEntity<Producto> createProducto(@RequestBody Producto producto){
		return new ResponseEntity<Producto>(this.productoService.create(producto), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idProducto}")
	public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto, @PathVariable int idProducto){
		if(idProducto != producto.getId()) {
			return ResponseEntity.badRequest().build();
		}else if(!this.productoService.existsProdcuto(idProducto)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.productoService.update(producto));
	}
	
	@DeleteMapping("/{idProducto}")
	public ResponseEntity<Producto> deleteProducto(@PathVariable int idProducto){
		if(this.productoService.deleteProducto(idProducto)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
