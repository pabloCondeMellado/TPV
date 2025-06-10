package com.daw.services.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.daw.persistence.entities.Estado;
import com.daw.persistence.entities.MetodoPago;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CuentaDto {
	private Integer id;
	private LocalDateTime fecha;
	private Double total;
	private MetodoPago metodoPago;
	private String usuario;
	private Estado estado;
	private String cliente;
	private List<DetalleOutputDto> productos;
}
