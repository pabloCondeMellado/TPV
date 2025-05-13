package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.DetalleCrudRepository;
import com.daw.persistence.entities.Detalle;

@Service
public class DetalleService {
	@Autowired
	private DetalleCrudRepository detalleCrudRepository;
	
	public List<Detalle> findAll(){
		return this.detalleCrudRepository.findAll();
	}
	
	public Optional<Detalle> findById(int idDetalle){
		return this.detalleCrudRepository.findById(idDetalle);
	}
	
	public boolean existsDetalle(int idDetalle) {
		return this.detalleCrudRepository.existsById(idDetalle);
	}
	
	public Detalle create(Detalle detalle) {
		return this.detalleCrudRepository.save(detalle);
	}
	
	public Detalle update(Detalle detalle) {
		return this.detalleCrudRepository.save(detalle);
	}
	
	public boolean deleteDetalle(int idDetalle) {
		boolean result = false;
		if(this.detalleCrudRepository.existsById(idDetalle)) {
			this.detalleCrudRepository.deleteById(idDetalle);
			result = true;
		}
		
		return result;
	}
}
