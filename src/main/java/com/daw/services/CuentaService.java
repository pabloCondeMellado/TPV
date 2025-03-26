package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.CuentaCrudRepository;
import com.daw.persistence.entities.Cuenta;

@Service
public class CuentaService {
	@Autowired
	private CuentaCrudRepository cuentaCrudRepository;
	
	public List<Cuenta> findAll(){
		return this.cuentaCrudRepository.findAll();
	}
	
	public Optional<Cuenta> findById(int idCuenta){
		return this.cuentaCrudRepository.findById(idCuenta);
	}
	
	public boolean existsCuenta(int idCuenta) {
		return this.cuentaCrudRepository.existsById(idCuenta);
	}
	
	public Cuenta create(Cuenta cuenta) {
		return this.cuentaCrudRepository.save(cuenta);
	}
	
	public Cuenta update(Cuenta cuenta) {
		return this.cuentaCrudRepository.save(cuenta);
	}
	
	public boolean deleteCuenta(int idCuenta) {
		boolean result = false;
		if(this.cuentaCrudRepository.existsById(idCuenta)) {
			this.cuentaCrudRepository.deleteById(idCuenta);
			result = true;
		}
		
		return result;
	}
}
