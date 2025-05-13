package com.daw.services.mappers;

import com.daw.persistence.entities.Detalle;
import com.daw.services.dto.DetalleInputDto;
import com.daw.services.dto.DetalleOutputDto;

public class DetalleMapper {
	public static DetalleOutputDto toDto(Detalle detalle) {
		DetalleOutputDto dto = new DetalleOutputDto();
		
		dto.setId(detalle.getId());
		dto.setIdProducto(detalle.getIdProducto());
		dto.setCantidad(detalle.getCantidad());
		dto.setPrecio(detalle.getSubtotal());
		dto.setProducto(detalle.getProducto().getNombre());
		
		return dto;
	}
	
	public static Detalle toEntity(DetalleInputDto dto) {
		Detalle d = new Detalle();
		
		d.setId(dto.getId());
		d.setIdProducto(dto.getIdProducto());
		d.setIdCuenta(dto.getIdCuenta());
		d.setCantidad(dto.getCantidad());
		
		return d;
	}
}
