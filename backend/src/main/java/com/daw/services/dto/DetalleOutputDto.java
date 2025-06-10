package com.daw.services.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetalleOutputDto {
	private Integer id;
	private Integer cantidad;
	private Double precio;
	private Double subtotal;
	private Integer idProducto;
	private String producto;
}
