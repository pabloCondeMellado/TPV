package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.InformeCrudRepository;
import com.daw.persistence.entities.Informe;

@Service
public class InformeService {
	@Autowired
	private InformeCrudRepository informeCrudRepository;
	
	public List<Informe> findAll(){
		return this.informeCrudRepository.findAll();
	}
	
	public Optional<Informe> findById(int idInforme){
		return this.informeCrudRepository.findById(idInforme);
	}
	
	public boolean existsInforme(int idInforme) {
		return this.informeCrudRepository.existsById(idInforme);
	}
	
	public Informe create(Informe informe) {
		return this.informeCrudRepository.save(informe);
	}
	
	public Informe update(Informe informe) {
		return this.informeCrudRepository.save(informe);
	}
	
	public boolean deleteInforme(int idInforme) {
		boolean result = false;
		if(this.informeCrudRepository.existsById(idInforme)) {
			this.informeCrudRepository.deleteById(idInforme);
			result = true;
		}
		
		return result;
	}
}
