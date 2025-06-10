package com.daw.services.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetalleInputDto {
	private Integer id;
	private Integer idCuenta;
	private Integer idProducto;
	private Integer cantidad;
}
