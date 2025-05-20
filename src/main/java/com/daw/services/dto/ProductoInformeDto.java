package com.daw.services.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductoInformeDto {
	private String nombre;
	private double precio;
	private int cantidad;
	private double subtotal;
}
