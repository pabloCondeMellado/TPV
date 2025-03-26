package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.ProductoCrudRepository;
import com.daw.persistence.entities.Producto;

@Service
public class ProductoService {
	@Autowired
	private ProductoCrudRepository productoCrudRepository;
	
	public List<Producto> findAll(){
		return this.productoCrudRepository.findAll();
	}
	
	public Optional<Producto> findById(int idProducto){
		return this.productoCrudRepository.findById(idProducto);
	}
	
	public boolean existsProdcuto(int idProducto) {
		return this.productoCrudRepository.existsById(idProducto);
	}
	
	public Producto create(Producto producto) {
		return this.productoCrudRepository.save(producto);
	}
	
	public Producto update(Producto producto) {
		return this.productoCrudRepository.save(producto);
	}
	
	public boolean deleteProducto(int idProducto) {
		boolean result = false;
		if(this.productoCrudRepository.existsById(idProducto)) {
			this.productoCrudRepository.deleteById(idProducto);
			result = true;
		}
		return result;
	}
}
