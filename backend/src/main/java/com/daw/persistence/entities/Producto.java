package com.daw.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(columnDefinition = "VARCHAR(30)")
	private String nombre;
	@Column(columnDefinition = "DOUBLE(6,2)")
	private Double precio;
	
	private Integer stock;
	@Column(columnDefinition = "VARCHAR(255)")
	private String imagen;
	
	private Sector sector;
}
